% Project needed bridges%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
bridgeDouble(X1,Y1,X2,Y2):-bridgeDouble(2,X1,Y1,X2,Y2).		%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beforeWorkBridgeDouble(X1,Y1,X2,Y2):- bridgeDouble(X1,Y1,X2,Y2).

toRemove(Row,Column):-history(Turn,Row,Column,Role),lastmove(Turn).

potentialNext(X,Y):- toRemove(Row,Column),adj(Row,Column,X,Y).

afterWorkCell(0,X,Y):- toRemove(X,Y).
afterWorkCell(Color,X,Y):- cell(Color,X,Y),toRemove(X1,Y1),X1!=X.
afterWorkCell(Color,X,Y):- cell(Color,X,Y),toRemove(X1,Y1),Y1!=Y.


afterWorkBridgeDouble(X1,Y1,X2,Y2):-  #count{X,Y: adj(X1,Y1,X,Y), adj(X2,Y2,X,Y) ,afterWorkCell(0,X,Y), not adj(X1,Y1,X2,Y2)}=2 ,afterWorkCell(2,X1,Y1),afterWorkCell(2,X2,Y2), X1<>X2.
afterWorkBridgeDouble(X1,Y1,X2,Y2):-  #count{X,Y: adj(X1,Y1,X,Y), adj(X2,Y2,X,Y) ,afterWorkCell(0,X,Y), not adj(X1,Y1,X2,Y2)}=2 ,afterWorkCell(2,X1,Y1),afterWorkCell(2,X2,Y2), Y1<>Y2.

maxCol(N):- N = #max{Col:cell(_,_,Col)}.

%bridge with the side CASE control(2)
afterWorkBridgeDouble(N1,N1,X,1):- maxCol(N),N1=N+1,afterWorkCell(2,X,1),X>0.
%N1 is N+2 because setting it to N+1 gives errors in computing pipes
afterWorkBridgeDouble(N1,N1,X,N-1):- maxCol(N),N1=N+2,afterWorkCell(2,X,N-1),X<N.

blockedBridge(X1,Y1,X2,Y2):- afterWorkBridgeDouble(X1,Y1,X2,Y2),not beforeWorkBridgeDouble(X1,Y1,X2,Y2).

response(X,Y):- blockedBridge(X1,Y1,X2,Y2),potentialNext(X,Y),adj(X1,Y1,X,Y),adj(X2,Y2,X,Y).
