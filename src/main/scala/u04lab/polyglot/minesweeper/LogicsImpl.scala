package u04lab.polyglot.minesweeper
import u04lab.code
import u04lab.polyglot.OptionToOptional

import scala.collection.mutable

case class Cell(position: Position, mine: Boolean, revealed: Boolean = false, flagged: Boolean = false):
  def reveal: Cell = this.copy(mine = mine, revealed = true)
  def flag: Cell = this.copy(mine = mine, revealed = revealed, flagged = !flagged)
  def isNeighbourOf(other: Cell): Boolean =
    !(position == other.position) && Math.abs(position.x - other.position.x) <= 1 && Math.abs(position.y - other.position.y) <= 1

object Cell:
  def mine(position: Position): Cell = Cell(position, mine = true)
  def empty(position: Position): Cell = Cell(position, mine = false)

case class Grid(size: Int, private val cells: mutable.Set[Cell]):
  def cellAt(position: Position): Option[Cell] =
    cells.find(_.position == position)
  def neighboursOf(position: Position): Set[Cell] =
    cellAt(position).map(c => cells.filter(_ isNeighbourOf c).toSet).getOrElse(Set.empty)
  def areAllEmptyCellsRevealed: Boolean =
    !cells.exists(c => !c.mine && !c.revealed)
  def hasMineBeenRevealed: Boolean =
    cells.exists(c => c.mine && c.revealed)
  private def replace(cell: Cell, newCell: Cell): Unit =
    cells.remove(cell)
    cells.add(newCell)
  def revealCellAt(position: Position): Unit =
    cellAt(position).foreach(c => replace(c, c.reveal))
  def flagCellAt(position: Position): Unit =
    cellAt(position).foreach(c => replace(c, c.flag))
  def getMinesAround(position: Position): Int =
    neighboursOf(position).count(_.mine)

object Grid:
  private def emptyField(size: Int): mutable.Set[Cell] =
    mutable.Set((for x <- 0 until size; y <- 0 until size yield Cell.empty(Position(x, y))): _*)
  def apply(size: Int, mines: Int): Grid =
    val cells = emptyField(size)
    val random = scala.util.Random
    while cells.count(_.mine) < mines do
      val position = Position(random.nextInt(size), random.nextInt(size))
      cells.remove(cells.find(_.position == position).get)
      cells.add(Cell.mine(position))
    Grid(size, cells)

class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:
  private val grid: Grid = Grid(size, mines)
  override def reveal(position: Position): Unit =
    grid.cellAt(position) match
      case Some(cell) if !cell.mine && !cell.revealed =>
        grid.revealCellAt(position)
        if grid.getMinesAround(position) == 0 then
          grid.neighboursOf(position).foreach(c => reveal(c.position))
      case _ => ()
  override def toggleFlag(position: Position): Unit =
    grid flagCellAt position
  override def hasWon: Boolean =
    grid.areAllEmptyCellsRevealed
  override def hasLost: Boolean =
    grid.hasMineBeenRevealed
  override def getCellStatus(position: Position): CellStatus =
    grid.cellAt(position)
      .orElse(Some(Cell.empty(position)))
      .map(c => CellStatus(c.revealed, c.flagged, c.mine, grid getMinesAround c.position))
      .get
