package u04lab.code

import junit.framework.Assert
import junit.framework.Assert.{assertEquals, assertFalse, assertNotNull, assertTrue}
import org.junit.Test
import u04lab.code.List
import u04lab.code.List.*
import u04lab.code.Option.{isEmpty, orElse}

class WarehouseTest:

  @Test def testCanBeCreated() =
    val warehouse = Warehouse()
    assertNotNull(warehouse)

  @Test def testStoreItem() =
    val warehouse = Warehouse()
    val item = Item(1, "Item1", cons("tag1", cons("tag2", Nil())))
    warehouse.store(item)
    assertTrue(warehouse.contains(item.code))

  @Test def testSearchItems() =
    val warehouse = Warehouse()
    for i <- 1 to 10 do
      warehouse.store(Item(i, s"Item$i", cons(s"tag$i", Nil())))
    val items = warehouse.searchItems("tag1")
    assertEquals(1, length(items))

  @Test def testRetrieveItem() =
    val warehouse = Warehouse()
    val item = Item(1, "Item1", cons("tag1", cons("tag2", Nil())))
    warehouse.store(item)
    val retrievedItem = warehouse.retrieve(item.code)
    assertFalse(isEmpty(retrievedItem))
    assertEquals(item, orElse(retrievedItem, null))




