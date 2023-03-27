package u04lab.code

import junit.framework.Assert
import junit.framework.Assert.{assertEquals, assertNotNull, assertTrue}
import org.junit.Test
import u04lab.code.List
import u04lab.code.List.*

class WarehouseTest:

  @Test def testCanBeCreated() =
    val warehouse = Warehouse()
    assertNotNull(warehouse)

  @Test def testStoreItem() =
    val warehouse = Warehouse()
    val item = Item(1, "Item1", cons("tag1", cons("tag2", Nil())))
    warehouse.store(item)
    assertTrue(warehouse.contains(item.code))


