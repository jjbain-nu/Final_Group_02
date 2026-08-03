# Pharma Supply Chain Portal

A Java Swing application for modelling a pharmaceutical supply chain. The system uses enterprises, organizations, role-specific work areas, and shared work-queue objects to track requests as they move between hospitals, wholesalers, manufacturers, suppliers, and transport.

## Technology

- Java 17
- Maven
- Swing / NetBeans GUI forms
- db4o embedded database (`Databank.db4o`)
- Java Faker for sample data

The application entry point is `ui.MainJFrame`.

## Run the application

### NetBeans

1. Open the project in NetBeans.
2. Clean and build the project.
3. Run the `Final_Group_02` project.

### Maven

Compile with:

```powershell
mvn clean compile
```

The configured main class is `ui.MainJFrame`. Use your IDE's run action or a Maven/Java run configuration that launches that class.

## Database and seed data

`Databank.db4o` is the local persisted database. On the first launch with no saved `EcoSystem`, `Business.ConfigureASystem` creates the demo network and data.

To reset the application data:

1. Close all running application instances. db4o permits only one process to open the database at a time.
2. Rename `Databank.db4o` to `Databank.backup.db4o` (recommended) or delete it.
3. Run the application again.

The next launch creates fresh seeded data. Changing `ConfigureASystem.java` does **not** update an already existing database.

## Seeded accounts

All credentials below are intended for local demonstration only.

| Area | Username | Password |
|---|---|---|
| System Administration | `sysadmin` | `sysadmin` |
| Lab Management | `labManager` | `sysadmin` |
| Supplier enterprise administrator | `SEA` | `1111` |
| Supplier administrator | `SA` | `1111` |
| Supplier material shipping | `MS` | `1111` |
| Manufacturer enterprise administrator | `MEA` | `1111` |
| Production administrator | `PA` | `1111` |
| Production operator | `PO` | `1111` |
| Quality assurance | `QA` | `1111` |
| Transport administrator | `transadmin` | `transadmin` |
| Driver | `driver` | `driver` |
| Hospital A/B/C procurement | `ProA`, `ProB`, `ProC` | `1111` |
| Hospital A/B/C pharmacy | `PhaA`, `PhaB`, `PhaC` | `1111` |
| Hospital A/B/C enterprise administrator | `HEA-A`, `HEA-B`, `HEA-C` | `1111` |
| Wholesaler A/B/C enterprise administrator | `WEA-A`, `WEA-B`, `WEA-C` | `1111` |
| Wholesaler A/B/C inventory administrator | `InventoryA`, `InventoryB`, `InventoryC` | `1111` |
| Wholesaler A/B/C shipping staff | `ShipStaffA`, `ShipStaffB`, `ShipStaffC` | `1111` |
| Wholesaler A/B/C shipping operator | `ShipOpA`, `ShipOpB`, `ShipOpC` | `1111` |

## Seeded enterprises and organizations

The default **Pharma Supply Chain Network** includes:

- Supplier A with a Supplier Organization, 100 sample materials/inventory rows, and 10 material requests.
- Manufacturer with Production and Quality Assurance organizations.
- FastLane Logistics with a Transport Organization and sample trucks.
- Hospital A, Hospital B, and Hospital C; each has Procurement and Pharmacy organizations.
- Wholesaler A, Wholesaler B, and Wholesaler C; each has Inventory and Shipping organizations.

Hospitals are initially paired with wholesalers as follows: Hospital A → Wholesaler A, Hospital B → Wholesaler B, and Hospital C → Wholesaler C.

Hospital pharmacies and wholesaler inventories share a seeded 10-medicine catalog. Each wholesaler has independent sample stock quantities and standard-stock levels.