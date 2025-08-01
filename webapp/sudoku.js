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

function render(grid) {
    const table = document.getElementById('grid');
    table.innerHTML = '';
    for (const row of grid) {
        const tr = document.createElement('tr');
        for (const val of row) {
            const td = document.createElement('td');
            td.textContent = val;
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
}

window.addEventListener('DOMContentLoaded', () => {
    document.getElementById('generate').addEventListener('click', () => {
        const grid = generateSudoku();
        render(grid);
    });
});
