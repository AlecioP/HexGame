% #include <potentialWinCalculator>%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% util																		  %
maxCol(N):- N = #max{Col:cell(_,_,Col)}.									  %
% general rule																  %
%																			  %
pipeUntil(X,Y):- pipeUntil(X1,Y1),bridgeDouble(2,X,Y,X1,Y1).				  %
pipeUntil(X,Y):- pipeUntil(X1,Y1),adj(X,Y,X1,Y1),cell(2,X,Y).				  %
%																			  %
% Basic step -> Bridge with left side of board 								  %
% (encoded with a bridge with a cell outside the board -> N1)				  %
pipeUntil(X,Y):-maxCol(N),N1=N+1,bridgeDouble(2,N1,N1,X,Y).					  %
%CASE control(2)															  %
pipeUntil(X,0):- control(2),cell(2,X,0).									  %
%																			  %
potentialWin :- control(2),pipeUntil(_,N), maxCol(N).						  %
%																			  %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
