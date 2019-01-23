% general rules

response(X,Y)|noResponse(X,Y):- cell(0,X,Y).

:- 1 <> #count{X,Y:response(X,Y)}.

% IF THERE IS A WALL AND THE MOVE DOESN'T BLOCK IT, THEN IT'S BAD

% CASE control(2) from LEFT to RIGHT

% stoppable walls
stoppableWall(X1,Y1,X2,Y2):-oppWall(X1,Y1,X2,Y2),control(2), 0<>#count{Row:cell(0,Row,Y1)}.

:~ control(2), stoppableWall(X1,Y1,X2,Y2), response(X,Y), Y<>Y1, Y<>Y2. [1@1]

% CASE control(1) from TOP to BOTTOM

% stoppable walls
stoppableWall(X1,Y1,X2,Y2):-oppWall(X1,Y1,X2,Y2),control(1), 0<>#count{Column:cell(0,X1,Column)}.

:~ control(1), stoppableWall(X1,Y1,X2,Y2), response(X,Y), X<>X1, X<>X2. [1@1]