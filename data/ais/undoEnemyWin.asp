% GUESS
response(X,Y)|noResponse(X,Y):- cell(0,X,Y).
:- 1 <> #count{X,Y:response(X,Y)}.

dim(N):- M = #max{C:cell(_,_,C)},N=M+1.


tb(X1,Y1,X2,Y2):-
cell1(1,X1,Y1),
cell1(1,X2,Y2),
2 = #count{X,Y:cell1(0,X,Y),adj(X,Y,X1,Y1),adj(X,Y,X2,Y2)},
not adj(X1,Y1,X2,Y2).

% ENCODING : (Row == N) means bridge with BOTTOM SIDE. (Row == N+1) means bridge with TOP SIDE

% 2Bridge with TOP SIDE OF BOARD


tb(R,C,N1,C1):- 
cell1(1,R,C),
C>0,
R=1,
C1 = C-1, 
cell1(0,0,C1),
cell1(0,0,C),
N1 = N+1, 
dim(N).

topSideBridge(X,Y,N1,Y1):-tb(X,Y,N1,Y1),N1=N+1,dim(N).

%IN THE MODEL THEY'RE 10. BUT IN THE PROGRAM THERE'S A RULE 
%TO MAKE THE PREDICATE SIMMETRICAL, THEN THE REAL NUMBER OF INSTANCES IS 10*2 = 20
%:- 20 <> #count{X,Y,X1,Y1:topSideBridge(X,Y,X1,Y1)}.


% 2Bridge with BOTTOM SIDE OF BOARD
tb(R,C,N,Col1):-
cell1(1,R,C), 
C<N-1,
R=N-2,
cell1(0,N1,Col1),
cell1(0,N1,C),
Col1 = C+1,
dim(N),  
N1 = N-1.

bottomSideBridge(X,Y,N,Y1):-tb(X,Y,N,Y1),dim(N). 

%:- 20 <> #count{X,Y,X1,Y1:bottomSideBridge(X,Y,X1,Y1)}.
  
% LAST RECURSIVE STEP ADJ
eWW:- strip(R,_), dim(N),R=(N-1).
% LAST RECURSIVE STEP 2BRIDGE
eWW:- strip(R,C), dim(N),R=(N-2),tb(R,C,N,C1),C1=C+1.


% Adjacence Inductive step
strip(X1,Y1):- strip(X,Y),cell1(1,X1,Y1),adj(X,Y,X1,Y1).
% 2Bridge Inductive step
strip(X1,Y1):- strip(X,Y),cell1(1,X1,Y1), tb(X,Y,X1,Y1).

%moveToUndo :- nE(R,C),response(R1,C1), R = R1 , C = C1.

%:~ not eWW. [1@1]

:- not eWW.


cell1(Color,R,C):- cell(Color,R,C).
%cell1(1,R,C):- nE(R,C).
cell1(1,R,C):- response(R,C). 

% FIRST RECURSIVE STEP
strip(0,C):- cell1(1,0,C).
strip(1,C):- cell1(1,1,C),tb(1,C,N1,C1),dim(N),N1=N+1,C1=C-1,C>0.

tb(R1,C1,R2,C2):- tb(R2,C2,R1,C1).
