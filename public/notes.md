# Game of Life Server

## Frontend

Options:
1. Canvas (HTML + CSS + JavaScript) 
2. SVG (HTML + CSS + D3JS)
3. Divs (HTML + CSS)

1. Canvas might be discouraged of some browsers because of privacy issues
2. Same as canvas, js can make privacy issues
3. Let's see if it's possible! :-)

## Try and Error

- well, nice grid, if it's `inline-block` divs in a fixed width div, but that makes line-breaks ...
- guess: `position: fixed` will do the work
- maybe script that populates screen with fixed divs and gives them appropriate position names (e.g. `class="17-22" for row 17 and column 22)