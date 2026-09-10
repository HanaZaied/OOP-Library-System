# OOP Library System

A menu-driven Java console application that manages a library's catalog of
items (Books, Magazines, DVDs), registers members, and handles borrowing
and returning — built to demonstrate core Object-Oriented Programming
concepts.

## What it does

- Maintains a **catalog** of library items and a set of **members**.
- Lets a member **borrow** and **return** items, enforcing rules such as:
    - an item that's already out can't be borrowed again,
    - a member can't exceed their personal borrowing limit,
    - a member can't return an item they never borrowed.
- Lists the catalog and prints a **report** (items by type, how many are
  currently out, total items ever created).
- **Bonus:** search items by title, list only available items.

All of this is driven from a simple text menu in the terminal.

## OOP concepts demonstrated

| Concept | Where |
|---|---|
| Abstraction / Encapsulation | `LibraryItem` — private fields, validated getters/setters |
| Inheritance | `Book`, `Magazine`, `DVD` extend `LibraryItem` |
| Polymorphism & Method Overriding | `getLoanPeriodDays()`, `getType()`, `displayInfo()` — `Library` never checks `instanceof`, it just calls the abstract methods and lets each subclass answer for itself |
| Constructors & Static members | `LibraryItem(title)` auto-generates ids via a static counter; `totalItemsCreated` is static |
| Collections | `Map<String, LibraryItem>` catalog, `Map<String, Member>` members, `Set<String>` borrowedIds, `List<LibraryItem>` per member |
| Custom exceptions | `LibraryException`, thrown for every broken business rule and always caught so the program never crashes |
| Input validation | Constructors/setters reject empty titles/names, non-positive numbers, etc.; the menu loop rejects non-numeric input |

## Project structure

```
OOP-Library-System/
└── src/
    ├── LibraryItem.java      (abstract class)
    ├── Book.java
    ├── Magazine.java
    ├── DVD.java
    ├── Member.java
    ├── LibraryException.java
    ├── Library.java          (the engine: catalog, members, borrow/return)
    └── Main.java              (console menu / entry point)
```

## How to compile and run

From the project root:

```bash
cd src
javac *.java -d ../out
cd ../out
java Main
```

You'll see:

```
===== Library Lending System =====
1. Add Item
2. Add Member
3. Borrow Item
4. Return Item
5. List Catalog
6. Report
7. Exit
8. Search by Title (bonus)
9. List Available Items (bonus)
Enter choice:
```

The app starts pre-loaded with one sample Book, Magazine, and DVD so you
can try option `5` or `3` right away.

## Sample session

```
Enter choice: 5
ITEM-1 | Clean Code | Book | loan: 21 days | available
ITEM-2 | National Geographic | Magazine | loan: 7 days | available
ITEM-3 | Inception | DVD | loan: 3 days | available

Enter choice: 3
Member id: M1
Item id: ITEM-1
Borrowed ITEM-1 to M1.

Enter choice: 6
---------- REPORT ----------
Total items    : 3
Currently out  : 1
Borrowed ids   : [ITEM-1]
Items by type  : {Book=1, Magazine=1, DVD=1}
Total created  : 3
----------------------------
```

## Design notes

- `Library.borrowItem()` / `returnItem()` / `listCatalog()` work only
  through the `LibraryItem` type — no `instanceof` chains. Adding a new
  kind of item (e.g. `AudioBook`) only means writing one new subclass;
  none of `Library` needs to change.
- `borrowed` on `LibraryItem` has no public setter — it can only change
  via `markBorrowed()` / `markReturned()`, keeping the invariant inside
  the class that owns it.
