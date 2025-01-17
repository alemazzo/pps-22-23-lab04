package u04lab.code

import org.junit.Assert.*
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

  @Test def testSearchItemsNotFound() =
    val warehouse = Warehouse()
    for i <- 1 to 10 do
      warehouse.store(Item(i, s"Item$i", cons(s"tag$i", Nil())))
    val items = warehouse.searchItems("tag11")
    assertEquals(0, length(items))

  @Test def testRetrieveItem() =
    val warehouse = Warehouse()
    val item = Item(1, "Item1", cons("tag1", cons("tag2", Nil())))
    warehouse.store(item)
    val retrievedItem = warehouse.retrieve(item.code)
    assertFalse(isEmpty(retrievedItem))
    assertEquals(item, orElse(retrievedItem, null))

  @Test def testRetrieveItemNotFound() =
    val warehouse = Warehouse()
    val retrievedItem = warehouse.retrieve(1)
    assertTrue(isEmpty(retrievedItem))

  @Test def testRemoveItem() =
    val warehouse = Warehouse()
    val item = Item(1, "Item1", cons("tag1", cons("tag2", Nil())))
    warehouse.store(item)
    warehouse.remove(item)
    assertFalse(warehouse.contains(item.code))

  @Test def testRemoveItemNotFound() =
    val warehouse = Warehouse()
    val item = Item(1, "Item1", cons("tag1", cons("tag2", Nil())))
    val item2 = Item(2, "Item2", cons("tag1", cons("tag2", Nil())))
    warehouse.store(item)
    warehouse.remove(item2)
    assertTrue(warehouse.contains(item.code))

  @Test def testCreateWithVarArgs() =
    val warehouse = Warehouse()
    val item1 = Item(1, "Item1", "tag1", "tag2")
    val item2 = Item(2, "Item2", "tag1", "tag3")
    warehouse.store(item1)
    warehouse.store(item2)
    val expectedTag1Items = cons(item2, cons(item1, Nil()))
    val expectedTag2Items = cons(item1, Nil())
    val expectedTag3Items = cons(item2, Nil())
    assertEquals(expectedTag1Items, warehouse.searchItems("tag1"))
    assertEquals(expectedTag2Items, warehouse.searchItems("tag2"))
    assertEquals(expectedTag3Items, warehouse.searchItems("tag3"))






