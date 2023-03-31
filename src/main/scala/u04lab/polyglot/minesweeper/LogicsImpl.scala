package u04lab.polyglot.minesweeper
import u04lab.code
import u04lab.polyglot.OptionToOptional

import scala.collection.mutable

case class Cell(position: Position, mine: Boolean, revealed: Boolean = false, flagged: Boolean = false):
  def reveal: Cell = this.copy(revealed = true)
  def flag: Cell = this.copy(flagged = !flagged)
  def isNeighbourOf(other: Cell): Boolean =
    !(position == other.position) && Math.abs(position.x - other.position.x) <= 1 && Math.abs(position.y - other.position.y) <= 1

object Cell:
  def mine(position: Position): Cell = Cell(position, mine = true)
  def empty(position: Position): Cell = Cell(position, mine = false)

class LogicsImpl(val size: Int, mines: Int) extends Logics:

  private var cells: List[Cell] = {
    val positions = getRandomPositions(mines)
    val elems = 0 until size flatMap (x => 0 until size map (y =>
      if positions.contains(Position(x, y)) then
        Cell.mine(Position(x, y))
      else
        Cell.empty(Position(x, y))))
    elems.toList
  }

  private def getRandomPositions(amount: Int): List[Position] =
    val random = scala.util.Random
    var positions = List.empty[Position]
    while positions.size < amount do
      val position = Position(random.nextInt(size), random.nextInt(size))
      if !positions.contains(position) then positions = position :: positions
    positions

  private def neighboursOf(position: Position): List[Cell] =
    cells.filter(_ isNeighbourOf Cell.empty(position))

  override def reveal(position: Position): Unit =
    cells.find(_.position == position) match
      case Some(cell) if !cell.revealed =>
        cells = cells.map(c => if c.position == position then c.reveal else c)
        if !cell.mine && neighboursOf(position).count(_.mine) == 0 then
          neighboursOf(position).foreach(c => reveal(c.position))
      case _ => ()

  override def toggleFlag(position: Position): Unit =
    cells = cells.map(c => if c.position == position then c.flag else c)

  override def getCellStatus(position: Position): CellStatus =
    cells.find(_.position == position) match
      case Some(cell) => CellStatus(cell.revealed, cell.flagged, cell.mine, neighboursOf(position).count(_.mine))
      case None => CellStatus(false, false, false, 0)

  override def hasWon: Boolean =
    cells.count(c => !c.mine && !c.revealed) == 0

  override def hasLost: Boolean =
    cells.exists(c => c.mine && c.revealed)
