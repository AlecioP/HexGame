% General rules

response(X,Y)|noResponse(X,Y):- cell(0,X,Y).

:- 1 <> #count{X,Y:response(X,Y)}.

% Board dimension
dim(N):- M = #max{C:cell(_,_,C)},N=M+1.
column(C):- cell(_,_,C).

% IF THERE IS A WALL AND THE MOVE DOESN'T BLOCK IT, THEN IT'S BAD

% CASE control(2) from LEFT to RIGHT

% stoppable walls
stoppableWall(X1,Y1,X2,Y2,"TOP-SIDE")    :- oppWall(X1,Y1,X2,Y2), control(2), 0<>#count{Row:cell(0,Row,Y1), Row<X1}.
stoppableWall(X1,Y1,X2,Y2,"BOTTOM-SIDE") :- oppWall(X1,Y1,X2,Y2), control(2), 0<>#count{Row:cell(0,Row,Y1), Row>X2}.

% Compute how important is to perform a block on some Column
weightBlockInColumn(Weight,C):- control(2), column(C), Weight = #count{R1,R2:stoppableWall(R1,C,R2,C,_),R1<R2}.

% MOST IMPORTANT : Stop long walls -> @3
:~ control(2), response(X,Y1), weightBlockInColumn(W,Y),Y1!=Y. [W@3]

% LESS IMPORTANT : Perform U-Block -> @1

edgeStoppableWall(X1,Y,X2,Y,"TOP-SIDE")   :- stoppableWall(X1,Y,X2,Y,"TOP-SIDE")   , control(2), cell(0,R,Y), R=X2+1.
edgeStoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE"):- stoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE"), control(2), cell(0,R,Y), R=X1-1.


:~ control(2), stoppableWall(X1,Y,X2,Y), response(X,Y), X<X2+3, cell(0,X2+3,Y), X1<X2, N = #count{Y: response(X,Y), stoppableWall(X1,Y,X2,Y),X>X1-3}. [1-N@1]
:~ control(2), stoppableWall(X1,Y,X2,Y), response(X,Y), X>X1-3, cell(0,X1-3,Y), X1<X2, N = #count{Y: response(X,Y), stoppableWall(X1,Y,X2,Y),X<X2+3}. [1-N@1]

