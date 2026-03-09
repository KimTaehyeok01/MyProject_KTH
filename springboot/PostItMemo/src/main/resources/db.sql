USE mydb;

CREATE TABLE memo (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,   -- 메모 PK
    title      VARCHAR(255)      NOT NULL DEFAULT '',  -- 제목
    content    TEXT              NOT NULL,             -- 내용
    color      ENUM('yellow','pink','blue','green','purple','orange')
                                  NOT NULL DEFAULT 'yellow', -- 메모 색상
    pos_x      INT               NOT NULL DEFAULT 0,  -- X 좌표
    pos_y      INT               NOT NULL DEFAULT 0,  -- Y 좌표
    z_index    INT               NOT NULL DEFAULT 0,  -- z-index
    created_at DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP
                                  ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


INSERT INTO memo (title, content, color, pos_x, pos_y, z_index, created_at)
VALUES
  ('환영합니다! 👋',
   '포스트잇을 드래그해서 이동하거나,\n내용을 직접 수정할 수 있어요.',
   'yellow',
   80, 60, 1,
   NOW()
  ),
  ('색상 변경',
   '각 노트 상단의 색상 버튼을 눌러\n색상을 바꿔보세요!',
   'blue',
   340, 120, 2,
   NOW()
  ),
  ('새 노트 추가',
   '상단 ''+ 새 포스트잇'' 버튼으로\n새로운 메모를 추가하세요.',
   'green',
   600, 50, 3,
   NOW()
  );

SELECT *FROM memo;
