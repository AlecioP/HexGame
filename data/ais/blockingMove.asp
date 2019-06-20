%LEGEND%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Sw = Stoppable Wall 

%perfectSw = Wall stoppable with the first step of an U-block
% movePerformsPerfectBlock -> mppb
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CASE control(2) from LEFT to RIGHT

empty(R,C):-cell(0,R,C).
occupied(R,C):-cell(Color,R,C),Color<>0.

response(X,Y)|noResponse(X,Y):- cell(0,X,Y).
:- 1 <> #count{X,Y:response(X,Y)}.

% Board dimension
dim(N):- M = #max{C:cell(_,_,C)},N=M+1.
column(C):- cell(_,_,C).

sw(X1,Y1,X2,Y2,"TOP-SIDE")    :- oppWall(X1,Y1,X2,Y2),  0<>#count{Row:cell(0,Row,Y1), Row<X1}.
sw(X1,Y1,X2,Y2,"BOTTOM-SIDE") :- oppWall(X1,Y1,X2,Y2),  0<>#count{Row:cell(0,Row,Y1), Row>X2}.

%Compute stoppable walls



perfectSW(X1,Y,X2,Y,"TOP-SIDE") :- 
% THERE IS A STOPPABLE WALL 
sw(X1,Y,X2,Y,"TOP-SIDE"), 
% THERE IS AN EMPTY CELL AT A GIVEN POSITION
empty(R,Y), R=X1-3,
% THERE IS ANY EMPTY CELL BETWEEN
not occupied(Rv1,Y),Rv1=X1-1,
not occupied(Rv2,Y),Rv2=X1-2.

perfectSW(X1,Y,X2,Y,"BOTTOM-SIDE") :- 
% THERE IS A STOPPABLE WALL 
sw(X1,Y,X2,Y,"BOTTOM-SIDE"), 
% THERE IS AN EMPTY CELL AT A GIVEN POSITION
cell(0,R,Y), R=X2+3,
% THERE IS ANY EMPTY CELL BETWEEN
not occupied(Rv1,Y),Rv1=X2+1,
not occupied(Rv2,Y),Rv2=X2+2.


mppb:- response(X,Y), perfectSW(X1,Y,X2,Y,"TOP-SIDE"), X=X1-3.
mppb:- response(X,Y), perfectSW(X1,Y,X2,Y,"BOTTOM-SIDE"), X=X2+3.

existsPSW:- perfectSW(_,_,_,_,_).

ublock(X,Y):-mppb,response(X,Y).

%Perform blocks creating bridges

moveCreatesBridge :- response(X2,Y2), cell(2,X1,Y1), 2 = #count{X,Y: adj(X,Y,X1,Y1), adj(X,Y,X2,Y2), cell(0,X,Y)},not adj(X1,Y1,X2,Y2).

% include Adjacent cells computation 

notBlocked(X1,Y,X2,Y,"TOP-SIDE"):- sw(X1,Y,X2,Y,"TOP-SIDE"), 0 = #count{Row:cell(2,Row,Y),Row<X1}.
notBlocked(X1,Y,X2,Y,"BOTTOM-SIDE"):- sw(X1,Y,X2,Y,"BOTTOM-SIDE"), 0 = #count{Row:cell(2,Row,Y),Row>X2}.

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

ublock(X,Y):-nextublock, response(X,Y).

%LEVELS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
																			  %
:~ response(X,Y1), weightBlockInColumn(W,Y),Y1!=Y. [W@5]					  %
																			  %
:~ notBlocked(X1,Y,X2,Y,"TOP-SIDE"), response(Row,_), Row>X1. [1@4]			  %
:~ notBlocked(X1,Y,X2,Y,"BOTTOM-SIDE"), response(Row,_), Row<X2. [1@4]		  %
																			  %
:~ existsPSW, not mppb. [1@3]							  %
																			  %
:~ not nextublock. [1@2]													  %
																			  %
:~ not moveCreatesBridge. [1@1]												  %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

