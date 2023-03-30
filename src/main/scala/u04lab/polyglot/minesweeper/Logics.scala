package u04lab.polyglot.minesweeper

case class Position(x: Int, y: Int)
case class CellStatus(isRevealed: Boolean, isFlagged: Boolean, isMine: Boolean, minesAround: Int)

trait Logics {
  def reveal(position: Position): Unit
  def toggleFlag(position: Position): Unit
  def getCellStatus(position: Position): CellStatus
  def hasWon: Boolean
  def hasLost: Boolean
}
