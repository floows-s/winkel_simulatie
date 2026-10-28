package objects.graph.Vertex;


// Note: It's from right to left and top to bottom. Start is right, end is left... This is also for the shelf numbering
public enum VertexID {
    SPAWN_POINT,
    ENTRANCE,
    SHOPPING_CART_PICKUP,

    // --- WALKWAY (LIGHT GREY TILES) ---
    WALKWAY_TOP_ENTRANCE, // Coupling between the top of the walkway and the entrance
    WALKWAY_TOP_START,
    WALKWAY_TOP_LOOKUP_KIOSK_STAND, // Coupling between the top of the walkway and the lookup kiosk stand
    WALKWAY_TOP_END,
    WALKWAY_AISLE_1,
    WALKWAY_AISLE_2,
    WALKWAY_STOCKROOM,
    WALKWAY_AISLE_3,
    WALKWAY_AISLE_4,
    WALKWAY_REGISTERS_QUEUE_END, // Coupling between the walkway and the end of the queue's of the registers
    WALKWAY_EXIT, // Coupling between the walkway and the exit

    // --- LOOKUP KIOSK STAND ----
    LOOKUP_KIOSK_STAND,

    // --- AISLE 1 (WOOD) ---
    AISLE_1_START,
    AISLE_1_SHELF_1,
    AISLE_1_SHELF_2,
    AISLE_1_SHELF_3,
    AISLE_1_SHELF_4,
    AISLE_1_SHELF_5,
    AISLE_1_SHELF_6,
    AISLE_1_END,

    // --- AISLE 2 (WOOD) ---
    AISLE_2_START,
    AISLE_2_SHELF_1,
    AISLE_2_SHELF_2,
    AISLE_2_SHELF_3,
    AISLE_2_SHELF_4,
    AISLE_2_SHELF_5,
    AISLE_2_SHELF_6,
    AISLE_2_END,

    // --- AISLE 3 (METAL) ---
    AISLE_3_START,
    AISLE_3_SHELF_1,
    AISLE_3_SHELF_2,
    AISLE_3_SHELF_3,
    AISLE_3_SHELF_4,
    AISLE_3_SHELF_5,
    AISLE_3_SHELF_6,
    AISLE_3_END,

    // --- AISLE 4 (COOL) ---
    AISLE_4_START,
    AISLE_4_SHELF_1,
    AISLE_4_SHELF_2,
    AISLE_4_SHELF_3,
    AISLE_4_SHELF_4,
    AISLE_4_SHELF_5,
    AISLE_4_SHELF_6,
    AISLE_4_END,

    // --- STOCKROOM ---
    STOCKROOM_ENTRANCE,
    STOCKROOM_MIDDLE,

    // Cool room (here the aisle number is from bottom to top, reason: entrance is at the bottom...)
    STOCKROOM_COOL_AISLE_1_MIDDLE,
    STOCKROOM_COOL_AISLE_1_SHELF_1,
    STOCKROOM_COOL_AISLE_1_SHELF_2,

    STOCKROOM_COOL_AISLE_2_MIDDLE,
    STOCKROOM_COOL_AISLE_2_SHELF_1,
    STOCKROOM_COOL_AISLE_2_SHELF_2,

    // Normal room
    STOCKROOM_AISLE_1_MIDDLE,
    STOCKROOM_AISLE_1_SHELF_1,
    STOCKROOM_AISLE_1_SHELF_2,

    STOCKROOM_AISLE_2_MIDDLE,
    STOCKROOM_AISLE_2_SHELF_1,
    STOCKROOM_AISLE_2_SHELF_2,

    // Loading dock
    STOCKROOM_LOADING_DOCK, // Coupling between stockroom and loading dock

    // --- LOADING DOCK ---
    LOADING_DOCK,

    // --- REGISTERS ---
    REGISTER_1_QUEUE_START,
    REGISTER_1_QUEUE_END,
    REGISTER_2_QUEUE_START,
    REGISTER_2_QUEUE_END,

    EXIT,
    DESPAWN_POINT
}
