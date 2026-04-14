import pygame
import sys
import random
import math

pygame.init()

SCREEN_W, SCREEN_H = 1200, 700
screen = pygame.display.set_mode((SCREEN_W, SCREEN_H))
pygame.display.set_caption("좀비 서바이벌")

clock = pygame.time.Clock()
font = pygame.font.SysFont("malgungothic", 20)
big_font = pygame.font.SysFont("malgungothic", 48)
mid_font = pygame.font.SysFont("malgungothic", 28)

player_img = pygame.image.load("./pygame/character.png").convert_alpha()
player_img = pygame.transform.scale(
    player_img, (player_img.get_width() // 5, player_img.get_height() // 5)
)

enemy_img = pygame.image.load("./pygame/enemy.png").convert_alpha()
enemy_img = pygame.transform.scale(
    enemy_img, (enemy_img.get_width() // 5, enemy_img.get_height() // 5)
)

monster_img = pygame.image.load("./pygame/monster.png").convert_alpha()
monster_img = pygame.transform.scale(
    monster_img, (monster_img.get_width() // 5, monster_img.get_height() // 5)
)

monster_boss_img = pygame.image.load("./pygame/monster_boss.png").convert_alpha()
monster_boss_img = pygame.transform.scale(
    monster_boss_img, (monster_boss_img.get_width() // 6, monster_boss_img.get_height() // 6)
)

round1_bg_img = pygame.image.load("./pygame/1round.png").convert()
round1_bg_img = pygame.transform.scale(round1_bg_img, (SCREEN_W, SCREEN_H))

round2_bg_img = pygame.image.load("./pygame/2round.png").convert()
round2_bg_img = pygame.transform.scale(round2_bg_img, (SCREEN_W, SCREEN_H))

round3_bg_img = pygame.image.load("./pygame/3round.png").convert()
round3_bg_img = pygame.transform.scale(round3_bg_img, (SCREEN_W, SCREEN_H))

sword_img_raw = pygame.image.load("./pygame/sickle.png").convert_alpha()
sword_h = int(player_img.get_height() * 1.3)
sword_w = int(sword_h * sword_img_raw.get_width() / sword_img_raw.get_height())
sword_img = pygame.transform.scale(sword_img_raw, (sword_w, sword_h))
sword_half_w = sword_img.get_width() / 2

pygame.mixer.music.load("./pygame/bgm.mp3")
pygame.mixer.music.set_volume(0.7)
pygame.mixer.music.play(-1)
attack_sound = pygame.mixer.Sound("./pygame/effect.wav")

PLAYER_SPEED = 4
ZOMBIE_SPEED = 1.2
ZOMBIE_SPAWN_INTERVAL = 120
ATTACK_RANGE = 450
DAMAGE_COOLDOWN = 60
ULTIMATE_KILL_REQ = 10
ULTIMATE_DURATION = 600
ARROW_SPEED = 8
ARROW_DAMAGE = 100
ARROW_SPAWN_INTERVAL = 5
ARROW_DIRECTIONS = 12
LASER_DAMAGE = 100
LASER_TICK_INTERVAL = 10

ROUND_CONFIG = {
    1: {
        "enemy_img": enemy_img,
        "enemy_hp": 50,
        "player_hp": 100,
        "attack_dmg": 20,
        "attack_cooldown": 30,
        "contact_damage": 10,
        "kill_goal": 100,
        "ult_type": "arrow",
        "round_name": "ROUND 1 - 좀비 습격",
        "bg_image": round1_bg_img,
        "bg_color": (25, 20, 30),
        "tile_color": (30, 25, 35),
    },
    2: {
        "enemy_img": monster_img,
        "enemy_hp": 200,
        "player_hp": 200,
        "attack_dmg": 50,
        "attack_cooldown": 15,
        "contact_damage": 10,
        "kill_goal": 100,
        "ult_type": "wave_arrow",
        "round_name": "ROUND 2 - 몬스터 침공",
        "bg_image": round2_bg_img,
        "bg_color": (30, 15, 15),
        "tile_color": (35, 20, 20),
    },
    3: {
        "enemy_img": monster_boss_img,
        "enemy_hp": 3000,
        "enemy_speed": 0.9,
        "player_hp": 260,
        "attack_dmg": 70,
        "attack_cooldown": 12,
        "contact_damage": 0,
        "kill_goal": 1,
        "ult_type": "blue_arrow",
        "ult_charge_mode": "hit",
        "ult_charge_req": 10,
        "round_name": "ROUND 3 - 보스 레이드",
        "bg_image": round3_bg_img,
        "bg_color": (10, 18, 35),
        "tile_color": (16, 24, 45),
        "boss_stationary": True,
        "boss_y_offset": -80,
        "boss_fire_interval": 110,
        "boss_fire_speed": 5.4,
        "boss_fire_damage": 18,
        "boss_fire_radius": 12,
        "spawn_interval": 1,
        "spawn_min_interval": 1,
        "spawn_decay": 0,
        "max_alive_enemies": 1,
    },
}


class Player:
    def __init__(self, round_num):
        cfg = ROUND_CONFIG[round_num]
        self.img = player_img
        self.x = SCREEN_W // 2
        self.y = SCREEN_H // 2
        self.max_hp = cfg["player_hp"]
        self.hp = self.max_hp
        self.attack_dmg = cfg["attack_dmg"]
        self.attack_cooldown = cfg["attack_cooldown"]
        self.rect = self.img.get_rect(center=(self.x, self.y))
        self.facing_right = True
        self.facing_angle = 0.0
        self.damage_timer = 0
        self.attack_timer = 0
        self.attacking = False
        self.attack_frame = 0
        self.score = 0
        self.round_kills = 0
        self.blink_timer = 0
        self.ult_kills = 0
        self.ult_charges = 0
        self.ult_active = False
        self.ult_timer = 0
        self.ult_spawn_timer = 0
        self.ult_type = cfg["ult_type"]
        self.ult_charge_mode = cfg.get("ult_charge_mode", "kill")
        self.ult_charge_req = max(1, cfg.get("ult_charge_req", ULTIMATE_KILL_REQ))

    def update(self, keys):
        dx, dy = 0, 0
        if keys[pygame.K_LEFT]:
            dx -= PLAYER_SPEED
        if keys[pygame.K_RIGHT]:
            dx += PLAYER_SPEED
        if keys[pygame.K_UP]:
            dy -= PLAYER_SPEED
        if keys[pygame.K_DOWN]:
            dy += PLAYER_SPEED

        if dx != 0 or dy != 0:
            self.facing_angle = math.degrees(math.atan2(-dy, dx))
            self.facing_right = dx >= 0

        if dx != 0 and dy != 0:
            dx *= 0.707
            dy *= 0.707

        self.x = max(0, min(SCREEN_W - self.rect.width, self.x + dx))
        self.y = max(0, min(SCREEN_H - self.rect.height, self.y + dy))
        self.rect.topleft = (self.x, self.y)

        if self.damage_timer > 0:
            self.damage_timer -= 1
        if self.attack_timer > 0:
            self.attack_timer -= 1
        if self.blink_timer > 0:
            self.blink_timer -= 1
        if self.attacking:
            self.attack_frame += 1
            if self.attack_frame > 14:
                self.attacking = False
                self.attack_frame = 0

    def attack(self, zombies):
        if self.attack_timer > 0:
            return []
        self.attack_timer = self.attack_cooldown
        self.attacking = True
        self.attack_frame = 0
        attack_sound.play()

        cx, cy = self.rect.center
        hit_zombies = []
        for zombie in zombies:
            zx, zy = zombie.rect.center
            dist = math.hypot(zx - cx, zy - cy)
            if dist <= ATTACK_RANGE:
                angle_to_zombie = math.degrees(math.atan2(-(zy - cy), zx - cx))
                diff = (angle_to_zombie - self.facing_angle + 180) % 360 - 180
                if abs(diff) <= 90:
                    hit_zombies.append(zombie)
        return hit_zombies

    def add_ult_progress(self, amount=1):
        self.ult_kills += amount
        while self.ult_kills >= self.ult_charge_req:
            self.ult_kills -= self.ult_charge_req
            self.ult_charges += 1

    def take_damage(self, amount):
        if self.damage_timer > 0:
            return
        self.hp -= amount
        self.damage_timer = DAMAGE_COOLDOWN
        self.blink_timer = 30
        if self.hp < 0:
            self.hp = 0

    def draw(self, surface):
        if self.blink_timer > 0 and (self.blink_timer // 4) % 2 == 1:
            return

        img = self.img
        if not self.facing_right:
            img = pygame.transform.flip(img, True, False)
        surface.blit(img, (self.x, self.y))

        if self.attacking:
            cx, cy = self.rect.center
            t = self.attack_frame / 14.0
            t_ease = (1 - math.cos(t * math.pi)) / 2

            swing_angle = self.facing_angle + 70 - (140 * t_ease)
            angle_rad = math.radians(swing_angle)

            rotated = pygame.transform.rotate(sword_img, swing_angle)

            offset_x = sword_half_w * math.cos(angle_rad)
            offset_y = -sword_half_w * math.sin(angle_rad)

            rot_rect = rotated.get_rect(center=(cx + offset_x, cy + offset_y))
            surface.blit(rotated, rot_rect)


class Zombie:
    def __init__(self, round_num):
        cfg = ROUND_CONFIG[round_num]
        self.round_num = round_num
        self.img = cfg["enemy_img"]
        self.hp = cfg["enemy_hp"]
        self.max_hp = cfg["enemy_hp"]
        self.stationary = cfg.get("boss_stationary", False)
        base_speed = cfg.get("enemy_speed", ZOMBIE_SPEED)
        self.speed = 0 if self.stationary else max(0.2, base_speed + random.uniform(-0.3, 0.3))
        self.hit_flash = 0
        self.killed_by_ult = False
        self.fire_timer = random.randint(0, cfg.get("boss_fire_interval", 90))
        self.fire_interval = cfg.get("boss_fire_interval", 90)
        self.fire_speed = cfg.get("boss_fire_speed", 0)
        self.fire_radius = cfg.get("boss_fire_radius", 10)

        w, h = self.img.get_width(), self.img.get_height()
        if self.stationary:
            self.x = SCREEN_W // 2 - w // 2
            self.y = SCREEN_H // 2 - h // 2 + cfg.get("boss_y_offset", -40)
        else:
            side = random.randint(0, 3)
            if side == 0:
                self.x = random.randint(-w * 2, -w)
                self.y = random.randint(0, SCREEN_H - h)
            elif side == 1:
                self.x = random.randint(SCREEN_W, SCREEN_W + w)
                self.y = random.randint(0, SCREEN_H - h)
            elif side == 2:
                self.x = random.randint(0, SCREEN_W - w)
                self.y = random.randint(-h * 2, -h)
            else:
                self.x = random.randint(0, SCREEN_W - w)
                self.y = random.randint(SCREEN_H, SCREEN_H + h)

        self.rect = self.img.get_rect(topleft=(self.x, self.y))
        self.facing_right = True

    def update(self, target_x, target_y):
        if self.stationary:
            self.facing_right = target_x >= self.rect.centerx
            if self.hit_flash > 0:
                self.hit_flash -= 1
            return

        dx = target_x - self.x
        dy = target_y - self.y
        dist = math.hypot(dx, dy)
        if dist > 0:
            self.x += (dx / dist) * self.speed
            self.y += (dy / dist) * self.speed
        self.rect.topleft = (int(self.x), int(self.y))
        self.facing_right = dx >= 0

        if self.hit_flash > 0:
            self.hit_flash -= 1

    def take_damage(self, amount):
        self.hp -= amount
        self.hit_flash = 8

    def is_dead(self):
        return self.hp <= 0

    def try_fire(self, target_x, target_y):
        if not self.stationary:
            return None
        self.fire_timer += 1
        if self.fire_timer < self.fire_interval:
            return None
        self.fire_timer = 0
        sx, sy = self.rect.center
        angle = math.atan2(target_y - sy, target_x - sx)
        return BossFire(
            sx,
            sy,
            math.cos(angle) * self.fire_speed,
            math.sin(angle) * self.fire_speed,
            damage=ROUND_CONFIG[self.round_num].get("boss_fire_damage", 18),
            radius=self.fire_radius,
        )

    def draw(self, surface):
        img = self.img
        if not self.facing_right:
            img = pygame.transform.flip(img, True, False)

        if self.hit_flash > 0:
            flash_img = img.copy()
            flash_img.fill((255, 80, 80), special_flags=pygame.BLEND_RGB_ADD)
            surface.blit(flash_img, (int(self.x), int(self.y)))
        else:
            surface.blit(img, (int(self.x), int(self.y)))

        bar_w = self.rect.width
        bar_h = 5
        bar_x = int(self.x)
        bar_y = int(self.y) - 10
        ratio = max(0, min(1, self.hp / self.max_hp))
        screen_rect = pygame.Rect(0, 0, SCREEN_W, SCREEN_H)

        full_bar_rect = pygame.Rect(bar_x, bar_y, bar_w, bar_h).clip(screen_rect)
        if full_bar_rect.width > 0 and full_bar_rect.height > 0:
            pygame.draw.rect(surface, (60, 60, 60), full_bar_rect)

        fill_bar_rect = pygame.Rect(bar_x, bar_y, int(bar_w * ratio), bar_h).clip(screen_rect)
        if fill_bar_rect.width > 0 and fill_bar_rect.height > 0:
            pygame.draw.rect(surface, (220, 50, 50), fill_bar_rect)


class Arrow:
    def __init__(
        self,
        x,
        y,
        angle,
        body_color=(255, 220, 50),
        tip_color=(255, 255, 150),
        impact_color=(255, 255, 100),
    ):
        self.x = float(x)
        self.y = float(y)
        rad = math.radians(angle)
        self.dx = ARROW_SPEED * math.cos(rad)
        self.dy = -ARROW_SPEED * math.sin(rad)
        self.life = 120
        self.hit_set = set()
        self.body_color = body_color
        self.tip_color = tip_color
        self.impact_color = impact_color

    def update(self):
        self.x += self.dx
        self.y += self.dy
        self.life -= 1

    def is_alive(self):
        return self.life > 0 and -50 < self.x < SCREEN_W + 50 and -50 < self.y < SCREEN_H + 50

    def draw(self, surface):
        end_x = self.x + self.dx * 3
        end_y = self.y + self.dy * 3
        pygame.draw.line(surface, self.body_color, (int(self.x), int(self.y)), (int(end_x), int(end_y)), 3)
        pygame.draw.circle(surface, self.tip_color, (int(end_x), int(end_y)), 4)


class WaveArrow:
    def __init__(self, x, y, angle):
        self.ox = float(x)
        self.oy = float(y)
        self.x = float(x)
        self.y = float(y)
        self.angle = angle
        rad = math.radians(angle)
        self.dx = ARROW_SPEED * math.cos(rad)
        self.dy = -ARROW_SPEED * math.sin(rad)
        self.perp_x = -self.dy
        self.perp_y = self.dx
        self.life = 120
        self.age = 0
        self.hit_set = set()
        self.trail = []

    def update(self):
        self.age += 1
        wave = math.sin(self.age * 0.3) * 4
        self.x += self.dx + self.perp_x * wave * 0.15
        self.y += self.dy + self.perp_y * wave * 0.15
        self.trail.append((int(self.x), int(self.y)))
        if len(self.trail) > 8:
            self.trail.pop(0)
        self.life -= 1

    def is_alive(self):
        return self.life > 0 and -50 < self.x < SCREEN_W + 50 and -50 < self.y < SCREEN_H + 50

    def draw(self, surface):
        for i, pos in enumerate(self.trail):
            alpha_ratio = (i + 1) / len(self.trail)
            r = int(255 * alpha_ratio)
            g = int(50 * alpha_ratio)
            b = int(30 * alpha_ratio)
            pygame.draw.circle(surface, (r, g, b), pos, 2)
        end_x = self.x + self.dx * 3
        end_y = self.y + self.dy * 3
        pygame.draw.line(surface, (255, 60, 40), (int(self.x), int(self.y)), (int(end_x), int(end_y)), 3)
        pygame.draw.circle(surface, (255, 120, 80), (int(end_x), int(end_y)), 5)


class BossFire:
    def __init__(self, x, y, dx, dy, damage=18, radius=12):
        self.x = float(x)
        self.y = float(y)
        self.dx = dx
        self.dy = dy
        self.damage = damage
        self.radius = radius
        self.life = 180
        self.trail = []

    def update(self):
        self.x += self.dx
        self.y += self.dy
        self.life -= 1
        self.trail.append((int(self.x), int(self.y)))
        if len(self.trail) > 6:
            self.trail.pop(0)

    def is_alive(self):
        return self.life > 0 and -60 < self.x < SCREEN_W + 60 and -60 < self.y < SCREEN_H + 60

    def collides(self, rect):
        closest_x = max(rect.left, min(int(self.x), rect.right))
        closest_y = max(rect.top, min(int(self.y), rect.bottom))
        dx = self.x - closest_x
        dy = self.y - closest_y
        return dx * dx + dy * dy <= self.radius * self.radius

    def draw(self, surface):
        for i, pos in enumerate(self.trail):
            ratio = (i + 1) / len(self.trail)
            trail_r = int(255 * ratio)
            trail_g = int(120 * ratio)
            trail_radius = max(1, int(self.radius * 0.45 * ratio))
            pygame.draw.circle(surface, (trail_r, trail_g, 30), pos, trail_radius)
        center = (int(self.x), int(self.y))
        pygame.draw.circle(surface, (255, 170, 40), center, self.radius)
        pygame.draw.circle(surface, (255, 90, 20), center, max(3, self.radius // 2))


def draw_hud(surface, player, round_num):
    cfg = ROUND_CONFIG[round_num]
    bar_w, bar_h = 200, 22
    bar_x, bar_y = 15, 15
    ratio = player.hp / player.max_hp

    pygame.draw.rect(surface, (40, 40, 40), (bar_x - 2, bar_y - 2, bar_w + 4, bar_h + 4), border_radius=4)
    pygame.draw.rect(surface, (60, 60, 60), (bar_x, bar_y, bar_w, bar_h), border_radius=3)

    hp_color = (50, 200, 80) if ratio > 0.5 else (220, 180, 30) if ratio > 0.25 else (220, 50, 50)
    pygame.draw.rect(surface, hp_color, (bar_x, bar_y, int(bar_w * ratio), bar_h), border_radius=3)

    hp_text = font.render(f"HP: {player.hp}/{player.max_hp}", True, (255, 255, 255))
    surface.blit(hp_text, (bar_x + bar_w + 10, bar_y))

    score_text = font.render(f"처치: {player.score}", True, (255, 255, 200))
    surface.blit(score_text, (bar_x, bar_y + 32))

    cd_ratio = player.attack_timer / player.attack_cooldown if player.attack_timer > 0 else 0
    cd_text_color = (150, 150, 150) if cd_ratio > 0 else (100, 255, 100)
    cd_label = font.render("공격 [D]", True, cd_text_color)
    surface.blit(cd_label, (SCREEN_W - 120, 15))
    pygame.draw.rect(surface, (40, 40, 40), (SCREEN_W - 122, 42, 104, 10), border_radius=3)
    pygame.draw.rect(
        surface, (100, 200, 255),
        (SCREEN_W - 120, 44, int(100 * (1 - cd_ratio)), 6), border_radius=2,
    )

    ult_y = bar_y + 58
    ult_bar_w = 200
    kill_ratio = min(1, player.ult_kills / player.ult_charge_req)
    pygame.draw.rect(surface, (40, 40, 40), (bar_x - 2, ult_y - 2, ult_bar_w + 4, 16), border_radius=4)
    pygame.draw.rect(surface, (60, 60, 60), (bar_x, ult_y, ult_bar_w, 12), border_radius=3)

    if player.ult_type == "arrow":
        ult_color = (255, 200, 50)
        ult_name = "화살"
    elif player.ult_type == "wave_arrow":
        ult_color = (100, 200, 255)
        ult_name = "웨이브 화살"
    else:
        ult_color = (80, 170, 255)
        ult_name = "푸른 화살"
    charge_label = "타격" if player.ult_charge_mode == "hit" else "처치"
    pygame.draw.rect(surface, ult_color, (bar_x, ult_y, int(ult_bar_w * kill_ratio), 12), border_radius=3)
    if player.ult_active:
        ult_text = font.render(f"필살기 발동중! ({player.ult_timer // 60 + 1}s)", True, ult_color)
    elif player.ult_charges > 0:
        ult_text = font.render(f"필살기 [S] x{player.ult_charges} 준비! ({ult_name})", True, ult_color)
    else:
        ult_text = font.render(
            f"필살기 [S] ({player.ult_kills}/{player.ult_charge_req} {charge_label}) ({ult_name})",
            True,
            (180, 180, 180),
        )
    surface.blit(ult_text, (bar_x + ult_bar_w + 10, ult_y - 2))

    kill_goal = cfg["kill_goal"]
    if kill_goal:
        progress_text = font.render(f"Round {round_num} - {player.round_kills}/{kill_goal}", True, (200, 200, 255))
    else:
        progress_text = font.render(f"Round {round_num} - 서바이벌!", True, (255, 150, 150))
    surface.blit(progress_text, (SCREEN_W - progress_text.get_width() - 15, 60))

    guide = font.render("방향키: 이동 | D: 공격 | S: 필살기", True, (180, 180, 180))
    surface.blit(guide, (SCREEN_W // 2 - guide.get_width() // 2, SCREEN_H - 30))


def game_over_screen(surface, score, round_num):
    overlay = pygame.Surface((SCREEN_W, SCREEN_H), pygame.SRCALPHA)
    overlay.fill((0, 0, 0, 180))
    surface.blit(overlay, (0, 0))

    title = big_font.render("GAME OVER", True, (220, 50, 50))
    surface.blit(title, (SCREEN_W // 2 - title.get_width() // 2, SCREEN_H // 2 - 80))

    round_msg = mid_font.render(f"도달 라운드: {round_num}", True, (200, 200, 255))
    surface.blit(round_msg, (SCREEN_W // 2 - round_msg.get_width() // 2, SCREEN_H // 2 - 30))

    score_msg = mid_font.render(f"총 처치: {score}마리", True, (255, 255, 255))
    surface.blit(score_msg, (SCREEN_W // 2 - score_msg.get_width() // 2, SCREEN_H // 2 + 10))

    restart_msg = font.render("R: 재시작 | ESC: 종료", True, (200, 200, 200))
    surface.blit(restart_msg, (SCREEN_W // 2 - restart_msg.get_width() // 2, SCREEN_H // 2 + 60))


def round_transition_screen(round_num):
    cfg = ROUND_CONFIG[round_num]
    fade_in = 60
    hold = 120
    fade_out = 60
    total = fade_in + hold + fade_out

    for frame in range(total):
        clock.tick(60)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

        if frame < fade_in:
            alpha = int(255 * frame / fade_in)
        elif frame < fade_in + hold:
            alpha = 255
        else:
            alpha = int(255 * (1 - (frame - fade_in - hold) / fade_out))

        screen.fill((0, 0, 0))
        overlay = pygame.Surface((SCREEN_W, SCREEN_H), pygame.SRCALPHA)

        title = big_font.render(cfg["round_name"], True, (255, 255, 255))
        title.set_alpha(alpha)
        overlay.blit(title, (SCREEN_W // 2 - title.get_width() // 2, SCREEN_H // 2 - 50))

        if round_num == 2:
            info = mid_font.render("HP 200 | 공격력 50 | 공속 2배 | 필살기: 웨이브 화살", True, (255, 100, 80))
        elif round_num == 3:
            info = mid_font.render("고정 보스 + 화염탄 | 필살기: 푸른 화살", True, (120, 190, 255))
        else:
            info = mid_font.render("HP 100 | 공격력 20 | 필살기: 화살", True, (255, 220, 100))
        info.set_alpha(alpha)
        overlay.blit(info, (SCREEN_W // 2 - info.get_width() // 2, SCREEN_H // 2 + 20))

        screen.blit(overlay, (0, 0))
        pygame.display.update()


def run_game():
    round_num = 1
    total_score = 0

    while True:
        round_transition_screen(round_num)
        cfg = ROUND_CONFIG[round_num]

        player = Player(round_num)
        player.score = total_score
        zombies = []
        arrows = []
        wave_arrows = []
        boss_fires = []
        spawn_timer = 0
        game_over = False
        round_clear = False
        particles = []

        while True:
            clock.tick(60)

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    sys.exit()
                elif event.type == pygame.KEYDOWN:
                    if game_over:
                        if event.key == pygame.K_r:
                            return True
                        elif event.key == pygame.K_ESCAPE:
                            pygame.quit()
                            sys.exit()
                    else:
                        if event.key == pygame.K_s:
                            if player.ult_charges > 0 and not player.ult_active:
                                player.ult_charges -= 1
                                player.ult_active = True
                                player.ult_timer = ULTIMATE_DURATION
                                player.ult_spawn_timer = 0
                                attack_sound.play()
                                if player.ult_type == "wave_arrow":
                                    wave_arrows.clear()
                        elif event.key == pygame.K_d:
                            hit_list = player.attack(zombies)
                            for zombie in hit_list:
                                zombie.take_damage(player.attack_dmg)
                                if player.ult_charge_mode == "hit":
                                    player.add_ult_progress()
                                for _ in range(5):
                                    particles.append({
                                        "x": zombie.rect.centerx,
                                        "y": zombie.rect.centery,
                                        "dx": random.uniform(-3, 3),
                                        "dy": random.uniform(-3, 3),
                                        "life": random.randint(10, 20),
                                        "color": (255, random.randint(50, 150), 50),
                                    })

            if game_over:
                game_over_screen(screen, player.score, round_num)
                pygame.display.update()
                continue

            if round_clear:
                break

            keys = pygame.key.get_pressed()
            player.update(keys)

            spawn_timer += 1
            spawn_interval = cfg.get("spawn_interval", ZOMBIE_SPAWN_INTERVAL)
            spawn_min_interval = cfg.get("spawn_min_interval", 30)
            spawn_decay = cfg.get("spawn_decay", 3)
            spawn_rate = max(spawn_min_interval, spawn_interval - player.round_kills * spawn_decay)
            max_alive_enemies = cfg.get("max_alive_enemies")
            if spawn_timer >= spawn_rate:
                spawn_timer = 0
                if max_alive_enemies is None or len(zombies) < max_alive_enemies:
                    zombies.append(Zombie(round_num))

            for zombie in zombies:
                zombie.update(player.rect.centerx, player.rect.centery)
                if zombie.rect.colliderect(player.rect):
                    player.take_damage(cfg.get("contact_damage", 10))
                fire = zombie.try_fire(player.rect.centerx, player.rect.centery)
                if fire:
                    boss_fires.append(fire)

            dead_zombies = [z for z in zombies if z.is_dead()]
            for z in dead_zombies:
                player.score += 1
                player.round_kills += 1
                if not z.killed_by_ult and player.ult_charge_mode == "kill":
                    player.add_ult_progress()
                player.hp = min(player.hp + 10, player.max_hp)
                for _ in range(8):
                    particles.append({
                        "x": z.rect.centerx, "y": z.rect.centery,
                        "dx": random.uniform(-4, 4), "dy": random.uniform(-4, 4),
                        "life": random.randint(15, 30),
                        "color": (100, 255, 100),
                    })
            zombies = [z for z in zombies if not z.is_dead()]

            # Arrow ultimate (Round 1 / Round 3)
            if player.ult_active and player.ult_type in ("arrow", "blue_arrow"):
                player.ult_timer -= 1
                player.ult_spawn_timer += 1
                if player.ult_spawn_timer >= ARROW_SPAWN_INTERVAL:
                    player.ult_spawn_timer = 0
                    cx, cy = player.rect.center
                    if player.ult_type == "blue_arrow":
                        body_color = (80, 170, 255)
                        tip_color = (170, 220, 255)
                        impact_color = (120, 200, 255)
                    else:
                        body_color = (255, 220, 50)
                        tip_color = (255, 255, 150)
                        impact_color = (255, 255, 100)
                    for i in range(ARROW_DIRECTIONS):
                        angle = (360 / ARROW_DIRECTIONS) * i + random.uniform(-10, 10)
                        arrows.append(Arrow(cx, cy, angle, body_color, tip_color, impact_color))
                if player.ult_timer <= 0:
                    player.ult_active = False

            for arrow in arrows:
                arrow.update()
                for zombie in zombies:
                    if id(zombie) in arrow.hit_set:
                        continue
                    dist = math.hypot(zombie.rect.centerx - arrow.x, zombie.rect.centery - arrow.y)
                    if dist < 30:
                        zombie.take_damage(ARROW_DAMAGE)
                        zombie.killed_by_ult = True
                        arrow.hit_set.add(id(zombie))
                        for _ in range(4):
                            particles.append({
                                "x": arrow.x, "y": arrow.y,
                                "dx": random.uniform(-3, 3), "dy": random.uniform(-3, 3),
                                "life": random.randint(8, 15),
                                "color": arrow.impact_color,
                            })
            arrows = [a for a in arrows if a.is_alive()]

            # Wave Arrow ultimate (Round 2)
            if player.ult_active and player.ult_type == "wave_arrow":
                player.ult_timer -= 1
                player.ult_spawn_timer += 1
                if player.ult_spawn_timer >= ARROW_SPAWN_INTERVAL:
                    player.ult_spawn_timer = 0
                    cx, cy = player.rect.center
                    for i in range(ARROW_DIRECTIONS):
                        angle = (360 / ARROW_DIRECTIONS) * i + random.uniform(-10, 10)
                        wave_arrows.append(WaveArrow(cx, cy, angle))
                if player.ult_timer <= 0:
                    player.ult_active = False

            for wa in wave_arrows:
                wa.update()
                for zombie in zombies:
                    if id(zombie) in wa.hit_set:
                        continue
                    dist = math.hypot(zombie.rect.centerx - wa.x, zombie.rect.centery - wa.y)
                    if dist < 30:
                        zombie.take_damage(ARROW_DAMAGE)
                        zombie.killed_by_ult = True
                        wa.hit_set.add(id(zombie))
                        for _ in range(4):
                            particles.append({
                                "x": wa.x, "y": wa.y,
                                "dx": random.uniform(-3, 3), "dy": random.uniform(-3, 3),
                                "life": random.randint(8, 15),
                                "color": (255, 80, 50),
                            })
            wave_arrows = [wa for wa in wave_arrows if wa.is_alive()]

            for fire in boss_fires:
                fire.update()
                if fire.collides(player.rect):
                    player.take_damage(fire.damage)
                    fire.life = 0
                    for _ in range(4):
                        particles.append({
                            "x": fire.x, "y": fire.y,
                            "dx": random.uniform(-2.5, 2.5), "dy": random.uniform(-2.5, 2.5),
                            "life": random.randint(10, 18),
                            "color": (255, random.randint(90, 170), 40),
                        })
            boss_fires = [fire for fire in boss_fires if fire.is_alive()]

            new_particles = []
            for p in particles:
                p["x"] += p["dx"]
                p["y"] += p["dy"]
                p["life"] -= 1
                if p["life"] > 0:
                    new_particles.append(p)
            particles = new_particles

            if player.hp <= 0:
                game_over = True

            kill_goal = cfg["kill_goal"]
            if kill_goal and player.round_kills >= kill_goal:
                round_clear = True

            # --- Draw ---
            bg_image = cfg.get("bg_image")
            if bg_image:
                screen.blit(bg_image, (0, 0))
            else:
                screen.fill(cfg["bg_color"])
                tile_size = 60
                for tx in range(0, SCREEN_W, tile_size):
                    for ty in range(0, SCREEN_H, tile_size):
                        if (tx // tile_size + ty // tile_size) % 2 == 0:
                            pygame.draw.rect(screen, cfg["tile_color"], (tx, ty, tile_size, tile_size))

            if player.ult_active:
                if player.ult_type == "arrow":
                    ult_alpha = int(30 + 20 * math.sin(player.ult_timer * 0.1))
                    ult_overlay = pygame.Surface((SCREEN_W, SCREEN_H), pygame.SRCALPHA)
                    ult_overlay.fill((255, 200, 50, ult_alpha))
                    screen.blit(ult_overlay, (0, 0))
                elif player.ult_type == "wave_arrow":
                    ult_alpha = int(20 + 15 * math.sin(player.ult_timer * 0.15))
                    ult_overlay = pygame.Surface((SCREEN_W, SCREEN_H), pygame.SRCALPHA)
                    ult_overlay.fill((255, 50, 30, ult_alpha))
                    screen.blit(ult_overlay, (0, 0))
                elif player.ult_type == "blue_arrow":
                    ult_alpha = int(25 + 15 * math.sin(player.ult_timer * 0.12))
                    ult_overlay = pygame.Surface((SCREEN_W, SCREEN_H), pygame.SRCALPHA)
                    ult_overlay.fill((70, 140, 255, ult_alpha))
                    screen.blit(ult_overlay, (0, 0))

            for zombie in zombies:
                zombie.draw(screen)

            player.draw(screen)

            for arrow in arrows:
                arrow.draw(screen)

            for wa in wave_arrows:
                wa.draw(screen)

            for fire in boss_fires:
                fire.draw(screen)

            for p in particles:
                pygame.draw.circle(screen, p["color"], (int(p["x"]), int(p["y"])), 3)

            draw_hud(screen, player, round_num)
            pygame.display.update()

        total_score = player.score
        if round_num + 1 in ROUND_CONFIG:
            round_num += 1
        else:
            round_num = max(ROUND_CONFIG.keys())


while run_game():
    pass
