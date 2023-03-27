package u04lab.code

import junit.framework.Assert
import junit.framework.Assert.assertNotNull
import org.junit.Test

class WarehouseTest:

  @Test def testCanBeCreated() =
    val warehouse = Warehouse()
    assertNotNull(warehouse)
