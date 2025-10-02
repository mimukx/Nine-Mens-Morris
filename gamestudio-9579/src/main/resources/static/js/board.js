let currentPlayer = 'X';
let phase = 'PLACING';
let selectedFrom = null;

const cell = document.getElementById(pos);
cell.innerHTML = ""; // очищаем

if (value === "X") {
    const piece = document.createElement("div");
    piece.className = "piece white-piece";
    cell.appendChild(piece);
} else if (value === "O") {
    const piece = document.createElement("div");
    piece.className = "piece black-piece";
    cell.appendChild(piece);
}


function updateBoard() {
    fetch("/board-data")
        .then(response => response.json())
        .then(data => {
            currentPlayer = data.currentPlayer;
            phase = data.phase;

            updateStatus(currentPlayer, phase);

            for (const [pos, value] of Object.entries(data.positions)) {
                const cell = document.getElementById(pos);
                cell.innerHTML = ''; // очистим

                if (value === 'X') {
                    const piece = document.createElement('div');
                    piece.classList.add('piece', 'white-piece');
                    cell.appendChild(piece);
                } else if (value === 'O') {
                    const piece = document.createElement('div');
                    piece.classList.add('piece', 'black-piece');
                    cell.appendChild(piece);
                }
            }
        });
}

function updateStatus(player, phase) {
    const playerDisplay = document.getElementById('currentPlayerName');
    const phaseDisplay = document.getElementById('currentPhase');

    playerDisplay.innerHTML = player === 'X'
        ? '<div class="piece white-piece"></div> (White)'
        : '<div class="piece black-piece"></div> (Black)';

    phaseDisplay.textContent = phase;
}

function placePiece(position) {
    if (phase === 'PLACING' || phase === 'REMOVE') {
        sendMove(position);
    } else if (phase === 'MOVING' || phase === 'FLYING') {
        const cell = document.getElementById(position);
        if (!selectedFrom) {
            if (cell.querySelector('.piece') &&
                ((currentPlayer === 'X' && cell.querySelector('.white-piece')) ||
                    (currentPlayer === 'O' && cell.querySelector('.black-piece')))
            ) {
                selectedFrom = position;
                cell.classList.add('selected');
            }
        } else {
            sendMove(`${selectedFrom}-${position}`);
            document.getElementById(selectedFrom).classList.remove('selected');
            selectedFrom = null;
        }
    }
}

function sendMove(command) {
    fetch("/move", {
        method: "POST",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: "position=" + command
    }).then(() => {
        updateBoard(); // обновим данные
    });
}

function resetGame() {
    fetch("/reset", { method: "POST" })
        .then(() => updateBoard());
}

window.onload = updateBoard;
