(function () {
    "use strict";
    /* another game of life */

    // initial functions

    var GRID_CELL_SIZE = 10;
    var click_is_on = false;

    function drawGridLine(start, length, horizontalFlag) {
        var canvas = document.getElementById('canvas');
        var context = canvas.getContext('2d');
        context.beginPath();
        if (horizontalFlag) {
            context.moveTo(0, start);
            context.lineTo(length, start);
        } else {
            context.moveTo(start, 0);
            context.lineTo(start, length);
        }
        context.strokeStyle = '#fff';
        context.stroke();
    }

    function drawGrid() {
        var canvas = document.getElementById('canvas');
        for (var i = canvas.height - GRID_CELL_SIZE; i >= 0; i = i - GRID_CELL_SIZE) {
            drawGridLine(i, canvas.height, true);
            drawGridLine(i, canvas.height, false);
        }
    }

    // event listener functions

    function createLife(canvas, mousePos) {
        var context = canvas.getContext('2d');
        var x = Math.floor(mousePos.x / GRID_CELL_SIZE);
        var y = Math.floor(mousePos.y / GRID_CELL_SIZE);
        context.rect(GRID_CELL_SIZE * x + 1, GRID_CELL_SIZE * y + 1, 8, 8);
        context.fillStyle = 'black';
        context.fill();
        // context.clearRect(0, 0, canvas.width, canvas.height);
    }

    function getMousePos(canvas, evt) {
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    }
    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');

    // event listener

    canvas.addEventListener('click', function(evt) {
        var mousePos = getMousePos(canvas, evt);
        var message = 'Mouse position: ' + mousePos.x + ',' + mousePos.y;
        createLife(canvas, mousePos);
    }, false);

    canvas.addEventListener('onmouseover', function(evt) {
        var mousePos = getMousePos(canvas, evt);
        var message = 'Mouse position: ' + mousePos.x + ',' + mousePos.y;
        createLife(canvas, mousePos);
    }, false);

    canvas.addEventListener('onmouseup', function(evt) {
        var mousePos = getMousePos(canvas, evt);
        var message = 'Mouse position: ' + mousePos.x + ',' + mousePos.y;
        createLife(canvas, mousePos);
    }, false);

   // function calls

    drawGrid();

})();