% General rules

response(X,Y)|noResponse(X,Y):- cell(0,X,Y).

:- 1 <> #count{X,Y:response(X,Y)}.

% Board dimension
dim(N):- M = #max{C:cell(_,_,C)},N=M+1.
column(C):- cell(_,_,C).

% CASE control(2) from LEFT to RIGHT

% stoppable walls
stoppableWall(X1,Y1,X2,Y2,"TOP-SIDE")    :- oppWall(X1,Y1,X2,Y2),  0<>#count{Row:cell(0,Row,Y1), Row<X1}.
stoppableWall(X1,Y1,X2,Y2,"BOTTOM-SIDE") :- oppWall(X1,Y1,X2,Y2),  0<>#count{Row:cell(0,Row,Y1), Row>X2}.

% Compute how important is to perform a block on some Column

:~ response(X,Y1), weightBlockInColumn(W,Y),Y1!=Y. [W@5]

%Compute stoppable walls

edgeStoppableWall(X1,Y1,X2,Y2,"TOP-SIDE"):-stoppableWall(X1,Y1,X2,Y2,"TOP-SIDE"),X3=X1-1, cell(Color,X3,Y1),Color!=1.
edgeStoppableWall(X1,Y1,X2,Y2,"BOTTOM-SIDE"):-stoppableWall(X1,Y1,X2,Y2,"BOTTOM-SIDE"),X3=X2+1, cell(Color,X3,Y1),Color!=1.

perfectStoppableWall(X1,Y,X2,Y,"TOP-SIDE") :- edgeStoppableWall(X1,Y,X2,Y,"TOP-SIDE"), cell(0,R,Y), R=X1-3.
perfectStoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE") :- edgeStoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE"), cell(0,R,Y), R=X2+3.

movePerformsPerfectBlock:- response(X,Y), perfectStoppableWall(X1,Y,X2,Y,"TOP-SIDE"), X=X1-3.
movePerformsPerfectBlock:- response(X,Y), perfectStoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE"), X=X2+3.

existsPSW:- perfectStoppableWall(_,_,_,_,_).

:~ existsPSW, not movePerformsPerfectBlock. [1@3]

ublock(X,Y):-movePerformsPerfectBlock,response(X,Y).

%Perform blocks creating bridges

moveCreatesBridge :- response(X2,Y2), cell(2,X1,Y1), 2 = #count{X,Y: adj(X,Y,X1,Y1), adj(X,Y,X2,Y2), cell(0,X,Y)},not adj(X1,Y1,X2,Y2).

:~ not moveCreatesBridge. [1@1]

% include Adjacent cells computation 

notBlocked(X1,Y,X2,Y,"TOP-SIDE"):- stoppableWall(X1,Y,X2,Y,"TOP-SIDE"), 0 = #count{Row:cell(2,Row,Y),Row<X1}.
notBlocked(X1,Y,X2,Y,"BOTTOM-SIDE"):- stoppableWall(X1,Y,X2,Y,"BOTTOM-SIDE"), 0 = #count{Row:cell(2,Row,Y),Row>X2}.

:~ notBlocked(X1,Y,X2,Y,"TOP-SIDE"), response(Row,_), Row>X1. [1@4]
:~ notBlocked(X1,Y,X2,Y,"BOTTOM-SIDE"), response(Row,_), Row<X2. [1@4]

weightBlockInColumn(W,C):- column(C), W = #count{SIDE : notBlocked(_,C,_,C,SIDE)}.

%UBLOCK 

lastMoveIndex(I):- I = #max{Turn:history(Turn,_,_,_)}.

lastMove(X,Y):-history(I,X,Y,_),lastMoveIndex(I).

nextublock :-ublock(X1,Y1),cell(Role,X1,Y1),Role<>0,lastMove(X2,Y2),response(X,Y),
%2Bridge Ublock and last enemy move
2 = #count{R,C:adj(X1,Y1,R,C),adj(X2,Y2,R,C),cell(0,R,C)},not adj(X1,Y1,X2,Y2),
%2Bridge Ublock and new move
2 = #count{R,C:adj(X1,Y1,R,C),adj(X,Y,R,C),cell(0,R,C)},not adj(X1,Y1,X,Y),
%2Bridge new move and last enemy move
2 = #count{R,C:adj(X,Y,R,C),adj(X2,Y2,R,C),cell(0,R,C)},not adj(X,Y,X2,Y2),
Y1<Y2,Y1<Y.

nextublock :-ublock(X1,Y1),cell(Role,X1,Y1),Role<>0,lastMove(X2,Y2),response(X,Y),
%2Bridge Ublock and last enemy move
2 = #count{R,C:adj(X1,Y1,R,C),adj(X2,Y2,R,C),cell(0,R,C)},not adj(X1,Y1,X2,Y2),
%2Bridge Ublock and new move
2 = #count{R,C:adj(X1,Y1,R,C),adj(X,Y,R,C),cell(0,R,C)},not adj(X1,Y1,X,Y),
%2Bridge new move and last enemy move
2 = #count{R,C:adj(X,Y,R,C),adj(X2,Y2,R,C),cell(0,R,C)},not adj(X,Y,X2,Y2),
Y1>Y2,Y1>Y.

:~ not nextublock. [1@2]

ublock(X,Y):-nextublock, response(X,Y).
