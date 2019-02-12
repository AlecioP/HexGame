%guess one move
smartMove(X,Y)|noSmartMove(X,Y):-cell(0,X,Y).

:- 1 <> #count{X,Y:smartMove(X,Y)}.

% #include <potentialWinCalculator>%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%																			  %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% #include <bridgeCalculator>%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Modified because the potential win should be computed considering the guessed move						 	%
cell(X,Y):-cell(_,X,Y).																							%
%																												%
% HEX CELLS ADJACENCY%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%														%
adj(X,Y,X,Y1):- cell(X,Y),cell(X,Y1),Y1=Y+1.			%														%
adj(X,Y,X,Y1):- cell(X,Y),cell(X,Y1),Y1=Y-1.			%														%
adj(X,Y,X1,Y):- cell(X,Y),cell(X1,Y),X1=X+1.			%														%
adj(X,Y,X1,Y):- cell(X,Y),cell(X1,Y),X1=X-1.			%														%
adj(X,Y,X1,Y1):-cell(X,Y),cell(X1,Y1),X1=X+1,Y1=Y+1.	%														%
adj(X,Y,X1,Y1):-cell(X,Y),cell(X1,Y1),X1=X-1,Y1=Y-1.	%														%
adj(X,Y,X1,Y1):-adj(X1,Y1,X,Y).							%														%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%														%
%																												%
% Update cells state after the guessed move																		%
cellAfter(State,Row,Column):- cell(State,Row,Column), not smartMove(Row,Column).								%
cellAfter(2,Row,Column):- smartMove(Row,Column).																%
%																												%
bridgeDouble(Color,X1,Y1,X2,Y2):-  #count{X,Y: adj(X1,Y1,X,Y), adj(X2,Y2,X,Y) ,cellAfter(0,X,Y), not adj(X1,Y1,X2,Y2)}=2 ,cellAfter(Color,X1,Y1),cellAfter(Color,X2,Y2), X1<>X2.
%																												%
bridgeDouble(Color,X1,Y1,X2,Y2):-  #count{X,Y: adj(X1,Y1,X,Y), adj(X2,Y2,X,Y) ,cellAfter(0,X,Y), not adj(X1,Y1,X2,Y2)}=2 ,cellAfter(Color,X1,Y1),cellAfter(Color,X2,Y2), Y1<>Y2.
%																												%
maxCol(N):- N = #max{Col:cell(_,_,Col)}.																		%
%																												%
%bridge with the side CASE control(2)																			%
bridgeDouble(2,N1,N1,X,1):- maxCol(N),N1=N+1,cellAfter(2,X,1),X>0.												%
%N1 is N+2 because setting it to N+1 gives errors in computing pipes											%
bridgeDouble(2,N1,N1,X,N-1):- maxCol(N),N1=N+2,cellAfter(2,X,N-1),X<N.											%
%																												%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%Now we should have 0 or 1 instances of "potentialWin"

:~ not potentialWin. [1@100]

% Answer sets with an instance of "potentialWin" are preferred

response(X,Y):- potentialWin, smartMove(X,Y).
