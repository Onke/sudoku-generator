function shuffle(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

function generateSudoku() {
    const grid = Array.from({ length: 9 }, () => Array(9).fill(0));

    function isValid(row, col, val) {
        for (let i = 0; i < 9; i++) {
            if (grid[row][i] === val || grid[i][col] === val) return false;
        }
        const startRow = Math.floor(row / 3) * 3;
        const startCol = Math.floor(col / 3) * 3;
        for (let r = 0; r < 3; r++) {
            for (let c = 0; c < 3; c++) {
                if (grid[startRow + r][startCol + c] === val) return false;
            }
        }
        return true;
    }

    function fill(r, c) {
        if (r === 9) return true;
        const nextR = c === 8 ? r + 1 : r;
        const nextC = c === 8 ? 0 : c + 1;
        const nums = shuffle([1, 2, 3, 4, 5, 6, 7, 8, 9]);
        for (const n of nums) {
            if (isValid(r, c, n)) {
                grid[r][c] = n;
                if (fill(nextR, nextC)) return true;
            }
        }
        grid[r][c] = 0;
        return false;
    }

    fill(0, 0);
    return grid;
}

function isValidPlacement(board, row, col, val) {
    for (let i = 0; i < 9; i++) {
        if (board[row][i] === val || board[i][col] === val) return false;
    }
    const startRow = Math.floor(row / 3) * 3;
    const startCol = Math.floor(col / 3) * 3;
    for (let r = 0; r < 3; r++) {
        for (let c = 0; c < 3; c++) {
            if (board[startRow + r][startCol + c] === val) return false;
        }
    }
    return true;
}

function countSolutions(board) {
    let count = 0;
    function solve(pos) {
        if (count > 1) return; // early exit if more than one solution
        if (pos === 81) {
            count++;
            return;
        }
        const row = Math.floor(pos / 9);
        const col = pos % 9;
        if (board[row][col] !== 0) {
            solve(pos + 1);
            return;
        }
        for (let n = 1; n <= 9; n++) {
            if (isValidPlacement(board, row, col, n)) {
                board[row][col] = n;
                solve(pos + 1);
                board[row][col] = 0;
            }
        }
    }
    solve(0);
    return count;
}

function hasUniqueSolution(board) {
    return countSolutions(board) === 1;
}

function generatePuzzle(difficulty) {
    const full = generateSudoku();
    const puzzle = full.map(row => row.slice());
    const ranges = {
        easy: [40, 45],
        medium: [32, 39],
        hard: [28, 31],
        korean: [22, 27]
    };
    const [minClues, maxClues] = ranges[difficulty] || ranges.easy;
    let clues = Math.floor(Math.random() * (maxClues - minClues + 1)) + minClues;
    let cellsToRemove = 81 - clues;
    let attempts = 0;
    while (cellsToRemove > 0 && attempts < 1000) {
        attempts++;
        const r = Math.floor(Math.random() * 9);
        const c = Math.floor(Math.random() * 9);
        if (puzzle[r][c] === 0) continue;
        const backup = puzzle[r][c];
        puzzle[r][c] = 0;
        if (!hasUniqueSolution(puzzle.map(row => row.slice()))) {
            puzzle[r][c] = backup;
        } else {
            cellsToRemove--;
        }
    }
    return puzzle;
}

function render(grid) {
    const table = document.getElementById('grid');
    table.innerHTML = '';
    for (const row of grid) {
        const tr = document.createElement('tr');
        for (const val of row) {
            const td = document.createElement('td');
            if (val !== 0) {
                td.textContent = val;
                td.classList.add('given');
            } else {
                td.textContent = '';
            }
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
}

window.addEventListener('DOMContentLoaded', () => {
    document.getElementById('generate').addEventListener('click', () => {
        const difficulty = document.getElementById('difficulty').value;
        const grid = generatePuzzle(difficulty);
        render(grid);
    });
});
