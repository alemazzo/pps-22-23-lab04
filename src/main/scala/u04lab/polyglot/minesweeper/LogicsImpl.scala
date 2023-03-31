package u04lab.polyglot.minesweeper
import u04lab.code
import u04lab.polyglot.OptionToOptional
import u04lab.code.{List, Option}
import u04lab.code.Option.{Some, None}
import u04lab.code.List.{append, cons, contains, filter, find, length, map}


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
    var elems = List.empty[Cell]
    0 until size flatMap (
      x => 0 until size map (
        y =>
          if contains(positions, Position(x, y)) then
            elems = cons(Cell.mine(Position(x, y)), elems)
          else
            elems = cons(Cell.empty(Position(x, y)), elems)
      )
    )
    elems
  }

  private def getRandomPositions(amount: Int): List[Position] =
    val random = scala.util.Random
    var positions = List.empty[Position]
    while length(positions) < amount do
      val position = Position(random.nextInt(size), random.nextInt(size))
      if !contains(positions, position) then positions = append(cons(position, List.empty), positions)
    positions

  private def neighboursOf(position: Position): List[Cell] =
    filter(cells)(_ isNeighbourOf Cell.empty(position))

  override def reveal(position: Position): Unit =
    find(cells)(_.position == position) match
      case Some(cell: Cell) if !cell.revealed =>
        cells = map(cells)(c => if c.position == position then c.reveal else c)
        if !cell.mine && length(filter(neighboursOf(position))(_.mine)) == 0 then
          map(neighboursOf(position))(c => reveal(c.position))
      case _ => ()

  override def toggleFlag(position: Position): Unit =
    cells = map(cells)(c => if c.position == position then c.flag else c)

  override def getCellStatus(position: Position): CellStatus =
    find(cells)(_.position == position) match
      case Some(cell) => CellStatus(cell.revealed, cell.flagged, cell.mine, length(filter(neighboursOf(position))(_.mine)))
      case None() => CellStatus(false, false, false, 0)

  override def hasWon: Boolean =
    length(filter(cells)(c => !c.mine && !c.revealed)) == 0

  override def hasLost: Boolean =
    length(filter(cells)(c => c.mine && c.revealed)) > 0
