% bridge(C1_X,C1_Y,C2_X,C2_Y)
% if controll(2) Ai is Blue and goes horizontally

% adj(X1,Y1,X2,Y2):-cell(_,X1,Y1),cell(_,X2,Y2),X2>X1-2,X2<X1+2,Y2>Y1-2,Y2<Y1+2,X1<>X2.
% adj(X1,Y1,X2,Y2):-cell(_,X1,Y1),cell(_,X2,Y2),X2>X1-2,X2<X1+2,Y2>Y1-2,Y2<Y1+2,Y1<>Y2.
% :- adj(X1,Y1,X2,Y2),X2=X1+1,Y2=Y1-1.
% :- adj(X1,Y1,X2,Y2),X2=X1-1,Y2=Y1+1.
% :- adj(X2,Y2,X1,Y1),X2=X1+1,Y2=Y1-1.
% :- adj(X2,Y2,X1,Y1),X2=X1-1,Y2=Y1+1.
cell(X,Y):-cell(_,X,Y).
adj(X,Y,X,Y1):- cell(X,Y),cell(X,Y1),Y1=Y+1.
adj(X,Y,X,Y1):- cell(X,Y),cell(X,Y1),Y1=Y-1.
adj(X,Y,X1,Y):- cell(X,Y),cell(X1,Y),X1=X+1.
adj(X,Y,X1,Y):- cell(X,Y),cell(X1,Y),X1=X-1.
adj(X,Y,X1,Y1):-cell(X,Y),cell(X1,Y1),X1=X+1,Y1=Y+1.
adj(X,Y,X1,Y1):-cell(X,Y),cell(X1,Y1),X1=X-1,Y1=Y-1.
adj(X,Y,X1,Y1):-adj(X1,Y1,X,Y).

bridgeDouble(X1,Y1,X2,Y2):-  #count{X,Y: adj(X1,Y1,X,Y), adj(X2,Y2,X,Y) ,cell(0,X,Y), not adj(X1,Y1,X2,Y2)}=2 ,cell(Color,X1,Y1),cell(Color,X2,Y2), control(Color), X1<>X2.
bridgeDouble(X1,Y1,X2,Y2):-  #count{X,Y: adj(X1,Y1,X,Y), adj(X2,Y2,X,Y) ,cell(0,X,Y), not adj(X1,Y1,X2,Y2)}=2 ,cell(Color,X1,Y1),cell(Color,X2,Y2), control(Color), Y1<>Y2.

maxCol(N):- N = #max{Col:cell(_,_,Col)}.

%bridge with the side CASE control(2)
bridgeDouble(N1,N1,X,1):- control(2),maxCol(N),N1=N+1,cell(2,X,1),X>0.
%N1 is N+2 because setting it to N+1 gives errors in computing pipes
bridgeDouble(N1,N1,X,N-1):- control(2),maxCol(N),N1=N+2,cell(2,X,N-1),X<N.

%bridge with the side CASE control(1)
bridgeDouble(N1,N1,1,Y):- control(1),maxCol(N),N1=N+1,cell(1,1,Y),Y>0.
%N1 is N+2 because setting it to N+1 gives errors in computing pipes
bridgeDouble(N1,N1,N-1,Y):- control(1),maxCol(N),N1=N+2,cell(1,N-1,Y),Y<N.
