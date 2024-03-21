import pygame as pg
import sys
from collections import deque


WALL = 1
PATH = 0
VISITING = 2
DONE = 3
VISITED = 4
DEST_PATH = 5


class Game:
    def __init__(self) -> None:
        pg.init()
        self.window_size = 900
        self.screen = pg.display.set_mode((600, 850))
        self.TILE_SIZE = 50
        self.clock = pg.time.Clock()

        self._dest = (5, 11)

        self._x = 12
        self._y = 6

        self._src = (0, 0)
        self._gird = [
            [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0],
            [0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0],
            [0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1],
            [0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0],
            [0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0],
        ]

        # self._src = (2, 5)
        # self._gird = [
        #     [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0],
        #     [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        #     [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        #     [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
        #     [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        #     [0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0],
        # ]
        self._init_bfs_dfs()

    def _get_grid_copy(self):
        cpy = []
        for i in range(len(self._gird)):
            r = []
            for j in range(len(self._gird[0])):
                r.append(self._gird[i][j])
            cpy.append(r)
        return cpy

    def update(self):
        pg.display.flip()
        self.clock.tick(24)

    def draw_grid(self, offset=0, window_size=300):
        for x in range(0, self.TILE_SIZE * self._x + 1, self.TILE_SIZE):
            pg.draw.line(self.screen, [50] * 3, (x, offset), (x, offset + window_size))

        for y in range(0, self.TILE_SIZE * self._y + 1, self.TILE_SIZE):
            pg.draw.line(
                self.screen, [50] * 3, (0, y + offset), (window_size + 300, y + offset)
            )

    def draw_board(self, board, offset=100):
        for i in range(6):
            for j in range(12):
                if board[i][j] == WALL:
                    self.draw_wall_block(i, j, "red", offset)
                elif board[i][j] == VISITING:
                    self.draw_wall_block(i, j, "yellow", offset)
                elif board[i][j] == DONE:
                    self.draw_wall_block(i, j, "green", offset)
                elif board[i][j] == VISITED:
                    self.draw_wall_block(i, j, "grey", offset)
                elif board[i][j] == DEST_PATH:
                    self.draw_wall_block(i, j, "blue", offset)
        if self._dfs_stk and offset == 100:
            cur_node = self._dfs_stk[-1]
            self.draw_wall_block(cur_node[0], cur_node[1], "orange", offset)

        if self._bfs_q and offset != 100:
            cur_node = self._bfs_q[0]
            self.draw_wall_block(cur_node[0], cur_node[1], "orange", offset)

    def draw_wall_block(self, i, j, color, offset=100):
        rect = pg.rect.Rect(
            [
                j * self.TILE_SIZE,
                i * self.TILE_SIZE + offset,
                self.TILE_SIZE - 2,
                self.TILE_SIZE - 2,
            ]
        )
        pg.draw.rect(self.screen, color, rect)

    def draw(self):
        self.screen.fill("black")
        font = pg.font.Font("freesansbold.ttf", 32)
        if self._dfs_steps:
            out_str = "DFS: Steps = " + str(self._dfs_steps)
        else:
            out_str = "DFS: Steps = --"

        text = font.render(out_str, True, "green", "black")

        self.screen.blit(text, (200, 20))
        self.draw_grid(100)
        self.draw_grid(500)

        if self._bfs_steps:
            out_str = "BFS: Steps = " + str(self._bfs_steps)
        else:
            out_str = "BFS: Steps = --"

        text = font.render(out_str, True, "green", "black")

        self.screen.blit(text, (200, 432))

        self.draw_board(self._dfs_grid)
        self.draw_board(self._bfs_grid, 500)
        # self.snake.draw()

    def _init_bfs_dfs(self):
        self._dfs_grid = self._get_grid_copy()
        self._bfs_grid = self._get_grid_copy()
        self._bfs_q = deque()
        self._dfs_stk = []
        self._dfs_map = dict()
        self._bfs_map = dict()
        self._bfs_steps = 0
        self._dfs_steps = 0
        self._dfs_stk.append(self._src)
        self._bfs_q.append(self._src)
        self._dfs_map[self._src] = None
        self._bfs_map[self._src] = None

    @staticmethod
    def _update_path(path_map, grid):
        cur = (5, 11)
        steps = 0

        while path_map[cur]:
            i, j = path_map[cur]
            grid[i][j] = DEST_PATH
            cur = path_map[cur]
            steps += 1
        return steps

    def _advance_serach(self):
        nbrs_dx = [[-1, 0], [0, 1], [1, 0], [0, -1]]
        is_valid = lambda x, y: (
            True if x >= 0 and x < 6 and y >= 0 and y < 12 else False
        )
        if self._dfs_stk:
            cur_node = self._dfs_stk.pop()
            self._dfs_grid[cur_node[0]][cur_node[1]] = VISITED
            if cur_node == self._dest:
                self._dfs_grid[-1][-1] = DONE
                self._dfs_steps = self._update_path(self._dfs_map, self._dfs_grid)
                self._dfs_stk = []
            else:
                x, y = cur_node
                for di, dj in nbrs_dx:
                    next_i, next_j = x + di, y + dj
                    if is_valid(next_i, next_j):
                        if self._dfs_grid[next_i][next_j] == PATH:
                            self._dfs_grid[next_i][next_j] = VISITING
                            self._dfs_stk.append((next_i, next_j))
                            self._dfs_map[(next_i, next_j)] = cur_node
        if self._bfs_q:
            cur_node = self._bfs_q.popleft()
            self._bfs_grid[cur_node[0]][cur_node[1]] = VISITED
            if cur_node == self._dest:
                self._bfs_grid[-1][-1] = DONE
                self._bfs_steps = self._update_path(self._bfs_map, self._bfs_grid)
                self._bfs_q = []
            else:
                x, y = cur_node
                for di, dj in nbrs_dx:
                    next_i, next_j = x + di, y + dj
                    if is_valid(next_i, next_j):
                        if self._bfs_grid[next_i][next_j] == PATH:
                            self._bfs_grid[next_i][next_j] = VISITING
                            self._bfs_q.append((next_i, next_j))
                            self._bfs_map[(next_i, next_j)] = cur_node

    def check_event(self):
        for evt in pg.event.get():
            if evt.type == pg.QUIT:
                pg.quit()
                sys.exit(0)

            if evt.type == pg.KEYDOWN:
                if evt.key == pg.K_RIGHT:
                    self._advance_serach()
                elif evt.key == pg.K_r:
                    self._init_bfs_dfs()
                elif evt.key == pg.K_l:
                    while self._dfs_stk or self._bfs_q:
                        self._advance_serach()
                        self.draw()
                        self.update()

    def run(self):
        while True:
            self.check_event()
            self.draw()
            self.update()


if __name__ == "__main__":
    g = Game()
    g.run()
