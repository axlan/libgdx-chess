package com.axlan.gdxchess;




enum Team
{
	WHITE,
	BLACK
}

enum PieceType
{
	PAWN,
	BISHOP,
	KNIGHT,
	CASTLE,
	KING,
	QUEEN
}


public class PieceInfo
{
	
	Team team;
	PieceType type;
	public PieceInfo(Team team, PieceType type) {
		super();
		this.team = team;
		this.type = type;
	}
	
	public String GetSpriteName()
	{
		return ((team==Team.WHITE)?"w":"b")+type.toString().toLowerCase();
	}
	
	
}

