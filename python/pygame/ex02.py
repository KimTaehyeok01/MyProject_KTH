import pygame
import sys

pygame.init()
screen = pygame.display.set_mode((1000, 700))
pygame.display.set_caption("이미지 표시하기")

# 이미지 로드 (같은 폴더에 'character.png'가 있어야 함)
character = pygame.image.load("./pygame/character.png")
# character = pygame.image.load("./character.png")

# 캐릭터 사이즈를 작게 조절 (원본의 1/5 크기로)
character = pygame.transform.scale(
    character, (character.get_width() // 5, character.get_height() // 5)
)

# FPS : Frame Per Second (30FPS로 떨어지면, 렉걸린다.)
# 1초에 화면 전체에 갱신하는 횟수, 1초에 30번 화면을 그린다.
# 사람의 눈이 30FPS이상만 되도 자연스러운 움직임으로 판단한다.

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

    screen.fill((0, 0, 0))  # 검은 배경
    screen.blit(character, (200, 100))  # 이미지 위치
    pygame.display.update()
