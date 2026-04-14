import pygame
import sys

# 파이썬으로 2D게임 만들기

# pygame 라이브러리 : 2D게임 제작 용도
# pip install pygame
# py -m pip install pygame


# 초기화
pygame.init()

# 화면 크기 설정
screen = pygame.display.set_mode((1000, 700))
pygame.display.set_caption("기본 창 띄우기")

# 메인 루프
while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
    screen.fill((255, 255, 255))  # 흰색 배경
    pygame.display.update()
