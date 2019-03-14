/*
 * TODO: 
 * Add En Peasant
 * Add Castling
 * Add promotion
 * 
 */

package com.axlan.gdxchess;

import java.util.ArrayList;


enum CheckState
{
	NONE,
	CHECK,
	MATE
}

public class Rules {
	private Rules(){}
	
	static void GetValidMoves(ArrayList<IntPoint2D> list,IntPoint2D selection,PieceInfo piece,Board board)
	{
		GetValidMoves(list, selection, piece, board,true);
	}
	
	static void GetValidMoves(ArrayList<IntPoint2D> list,IntPoint2D selection,PieceInfo piece,Board board,boolean checkcheck)
	{
		
		switch(piece.type)
		{
			case PAWN:
				GetValidMovesPawn(list,selection,piece.team,board);
				break;
			case BISHOP:
				GetValidMovesBishop(list,selection,piece.team,board);
				break;
			case KNIGHT:
				GetValidMovesKnight(list,selection,piece.team,board);
				break;
			case CASTLE:
				GetValidMovesCastle(list,selection,piece.team,board);
				break;
			case KING:
				GetValidMovesKing(list,selection,piece.team,board);
				break;
			case QUEEN:
				GetValidMovesQueen(list,selection,piece.team,board);
				break;
		}
		if(checkcheck)
		{
			CheckCheck( list,selection,piece.team, board);
		}
	}
	
	private static void CheckCheck(ArrayList<IntPoint2D> list,IntPoint2D selection,Team team,Board board)
	{
		
		for(int i=0;i<list.size();i++)
		{
			IntPoint2D move=list.get(i);
			
			board.MovePiece(selection, move);
			
			if(CheckCheck(team,board)==CheckState.CHECK)
			{
				list.remove(i);
				i--;
			}
			
			board.UndoMove();
			
		}
		
		
	}
	
	public static CheckState CheckCheck(Team team,Board board)
	{
		ArrayList<IntPoint2D> list=new ArrayList<IntPoint2D>();
		
		IntPoint2D king=board.GetKing(team);
		
		
		
		for(PieceType type:PieceType.values())
		{
			PieceInfo info=new PieceInfo(team, type);
			GetValidMoves(list,king,info,board,false);
			for(IntPoint2D move:list)
			{
				PieceInfo target= board.GetPiece(move);
				if(target!=null&&target.type==type)
				{
					return CheckState.CHECK;
				}
			}
			list.clear();
		}
		
		
		return CheckState.NONE;
	}
	
	private static void GetValidMovesQueen(ArrayList<IntPoint2D> list,IntPoint2D selection,Team team,Board board)
	{
		for(int xDir=-1;xDir<=1;xDir++)
		{
			for(int yDir=-1;yDir<=1;yDir++)
			{
				if(xDir==0&&yDir==0)
				{
					continue;
				}
				IntPoint2D move=selection;
				
				while(true)
				{
					move=move.Transpose(xDir, yDir);
					if(!board.IsInBounds(move))
					{
						break;
					}
					
					PieceInfo target= board.GetPiece(move);
					
					if(target!=null)
					{
						if(target.team!=team)
						{
							list.add(move);							
						}
						break;
					}
					
					list.add(move);
				}
			}
		}
	}
	

	private static void GetValidMovesKnight(ArrayList<IntPoint2D> list,IntPoint2D selection,Team team,Board board)
	{
		
		for(int direction=0;direction<2;direction++)
		{
			for(int longDir=-2;longDir<=2;longDir+=4)
			{
				for(int shortDir=-1;shortDir<=1;shortDir+=2)
				{
					IntPoint2D move;
					if(direction==0)
					{
						move=selection.Transpose(longDir, shortDir);
					}
					else
					{
						move=selection.Transpose(shortDir,longDir);
					}
					PieceInfo target=board.GetPiece(move);
					if(board.IsInBounds(move)&&(target==null||target.team!=team))
					{
						list.add(move);
					}
				}
			}
			
		}
	}
	
	private static void GetValidMovesKing(ArrayList<IntPoint2D> list,IntPoint2D selection,Team team,Board board)
	{
		for(int xDir=-1;xDir<=1;xDir++)
		{
			for(int yDir=-1;yDir<=1;yDir++)
			{
				if(xDir==0&&yDir==0)
				{
					continue;
				}
				IntPoint2D move=selection.Transpose(xDir, yDir);
				PieceInfo target=board.GetPiece(move);
				if(board.IsInBounds(move)&&(target==null||target.team!=team))
				{
					list.add(move);
				}
			}
		}
		
	}
	
	
	private static void GetValidMovesCastle(ArrayList<IntPoint2D> list,IntPoint2D selection,Team team,Board board)
	{
		for(int direction=0;direction<2;direction++)
		{
			for(int direction2=-1;direction2<=1;direction2+=2)
			{
				IntPoint2D move=selection;
				while(true)
				{
					if(direction==0)
					{
						move=move.Transpose(direction2, 0);
					}
					else
					{
						move=move.Transpose(0, direction2);
					}
					
					if(!board.IsInBounds(move))
					{
						break;
					}
					
					PieceInfo target= board.GetPiece(move);
					
					if(target!=null)
					{
						if(target.team!=team)
						{
							list.add(move);							
						}
						break;
					}
					
					list.add(move);
				}
			}
		}
	}
	
	
	private static void GetValidMovesBishop(ArrayList<IntPoint2D> list,IntPoint2D selection,Team team,Board board)
	{
		for(int xDir=-1;xDir<=1;xDir+=2)
		{
			for(int yDir=-1;yDir<=1;yDir+=2)
			{
				IntPoint2D move=selection;
				while(true)
				{
					move=move.Transpose(xDir, yDir);
					if(!board.IsInBounds(move))
					{
						break;
					}
					
					PieceInfo target= board.GetPiece(move);
					
					if(target!=null)
					{
						if(target.team!=team)
						{
							list.add(move);							
						}
						break;
					}
					
					list.add(move);
				}
			}
		}
	}
	
	
	private static void GetValidMovesPawn(ArrayList<IntPoint2D> list,IntPoint2D selection,Team team,Board board)
	{
		int direction=(team==Team.BLACK)?1:-1;
		
		IntPoint2D normalMove=selection.Transpose(0, direction);
		
		if(board.IsInBounds(normalMove)&&board.GetPiece(normalMove)==null)
		{
			list.add(normalMove);
		}
		
		int startRow=(team==Team.BLACK)?1:6;
		
		IntPoint2D firstMove=selection.Transpose(0, 2*direction);
		
		if(selection.getY()==startRow&&board.IsInBounds(firstMove)&&board.GetPiece(firstMove)==null)
		{
			list.add(firstMove);
		}
		
		for(int i=-1;i<=1;i+=2)
		{
			IntPoint2D captureMove=selection.Transpose(i, direction);
			
			PieceInfo target=board.GetPiece(captureMove);
			
			if(board.IsInBounds(captureMove)&&target!=null&&target.team!=team)
			{
				list.add(captureMove);
			}
			
			
		}
		
		
	}
	
	
	
}
