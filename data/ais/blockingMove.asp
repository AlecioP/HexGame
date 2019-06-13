% General rules

response(X,Y)|noResponse(X,Y):- cell(0,X,Y).

:- 1 <> #count{X,Y:response(X,Y)}.

% Board dimension
dim(N):- M = #max{C:cell(_,_,C)},N=M+1.
column(C):- cell(_,_,C).

% IF THERE IS A WALL AND THE MOVE DOESN'T BLOCK IT, THEN IT'S BAD

% CASE control(2) from LEFT to RIGHT

% stoppable walls
stoppableWall(X1,Y1,X2,Y2,"TOP-SIDE")    :- oppWall(X1,Y1,X2,Y2),  0<>#count{Row:cell(0,Row,Y1), Row<X1}.
stoppableWall(X1,Y1,X2,Y2,"BOTTOM-SIDE") :- oppWall(X1,Y1,X2,Y2),  0<>#count{Row:cell(0,Row,Y1), Row>X2}.

% Compute how important is to perform a block on some Column
%weightBlockInColumn(Weight,C):- column(C), Weight = #count{R1,R2:stoppableWall(R1,C,R2,C,_),R1<R2}, Weight<>0.

% MOST IMPORTANT : Stop long walls -> @3
:~ response(X,Y1), weightBlockInColumn(W,Y),Y1!=Y. [W@3]

% LESS IMPORTANT : Perform U-Block -> @1

edgeStoppableWall(X1,Y1,X2,Y2,"TOP-SIDE"):-stoppableWall(X1,Y1,X2,Y2,"TOP-SIDE"),X3=X1-1, cell(Color,X3,Y1),Color!=1.
edgeStoppableWall(X1,Y1,X2,Y2,"BOTTOM-SIDE"):-stoppableWall(X1,Y1,X2,Y2,"BOTTOM-SIDE"),X3=X2+1, cell(Color,X3,Y1),Color!=1.

perfectStoppableWall(X1,Y,X2,Y,"TOP-SIDE") :- edgeStoppableWall(X1,Y,X2,Y,"TOP-SIDE"), cell(0,R,Y), R=X1-3.
perfectStoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE") :- edgeStoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE"), cell(0,R,Y), R=X2+3.

movePerformsPerfectBlock:- response(X,Y), perfectStoppableWall(X1,Y,X2,Y,"TOP-SIDE"), X=X1-3.
movePerformsPerfectBlock:- response(X,Y), perfectStoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE"), X=X2+3.

existsPSW:- perfectStoppableWall(_,_,_,_,_).

:~ existsPSW, not movePerformsPerfectBlock. [1@1]

% Test -> Perform blocks creating bridges

moveCreatesBridge :- response(X2,Y2), cell(2,X1,Y1), 2 <= #count{X,Y: adj(X,Y,X1,Y1), adj(X,Y,X2,Y2), cell(0,X,Y)}.

:~ not moveCreatesBridge. [1@1]

% include Adjacent cells computation 




%TEST

notBlocked(X1,Y,X2,Y,"TOP-SIDE"):- stoppableWall(X1,Y,X2,Y,"TOP-SIDE"), 0 = #count{Row:cell(2,Row,Y),Row<X1}.
notBlocked(X1,Y,X2,Y,"BOTTOM-SIDE"):- stoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE"), 0 = #count{Row:cell(2,Row,Y),Row>X2}.

:~ notBlocked(X1,Y,X2,Y,"TOP-SIDE"), response(Row,_), Row>X1. [1@2]
:~ notBlocked(X1,Y,X2,Y,"BOTTOM-SIDE"), response(Row,_), Row<X2. [1@2]

weightBlockInColumn(W,C):- column(C), W = #count{SIDE : notBlocked(_,C,_,C,SIDE)}.
