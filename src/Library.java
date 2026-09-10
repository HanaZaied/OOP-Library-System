import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
public class Library {
    /**
     * Fields:
     * • Map<String, LibraryItem> catalog – item id → item (lets you look an item up quickly)
     * • Map<String, Member> members – member id → member
     * • Set<String> borrowedIds – the ids of every item currently out (a Set, so no duplicates)
     */
    private Map<String, LibraryItem> catalog = new HashMap<>();
    private Map<String, Member> members = new LinkedHashMap<>();
    public Set<String> borrowedIds = new HashSet<>();


    public void addItem(LibraryItem item){
        catalog.put(item.getId(),item);
    }

    public void addMember(Member m) {
        members.put(m.getMemberId(), m);
    }

    public void borrowItem(String memberId, String itemId) throws LibraryException{
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException(String.format("Member %s does not exist.", itemId));
        }
        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new LibraryException(String.format("Item %s does not exist.", itemId));
        }
        if (item.isBorrowed()) {
            throw new LibraryException(String.format("Item %s is already out.", itemId));
        }
        if (!member.canBorrowMore()) {
            throw new LibraryException(String.format("Member %s has reached their borrowing limit (%d).", memberId, member.getMaxAllowed()));
        }
        item.markBorrowed();
        member.addBorrowedItem(item);
        borrowedIds.add(item.getId());
    }

    public void returnItem(String memberId, String itemId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException(String.format("Member %s does not exist.", memberId));
        }
        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new LibraryException(String.format("Item %s does not exist",itemId));
        }
        if (!member.isHolding(item)) {
            throw new LibraryException(String.format("Member %s did not borrow item %s.", memberId, itemId));
        }

        item.markReturned();
        member.removeBorrowedItem(item);
        borrowedIds.remove(item.getId());
    }

    public void listCatalog() {
        if (catalog.isEmpty()) {
            System.out.println("The catalog is empty.");
            return;
        }
        for (LibraryItem item : catalog.values()) {
            item.displayInfo();
        }
    }

    public void printReport() {
        Map<String, Integer> byType = new LinkedHashMap<>();
        for (LibraryItem item : catalog.values()) {
            byType.merge(item.getType(), 1, Integer::sum);
        }

        System.out.println("---------- REPORT ----------");
        System.out.println("Total items    : " + catalog.size());
        System.out.println("Currently out  : " + borrowedIds.size());
        System.out.println("Borrowed ids   : " + borrowedIds);
        System.out.println("Items by type  : " + byType);
        System.out.println("Total created  : " + LibraryItem.getTotalItemsCreated());
        System.out.println("----------------------------");
    }

    public void searchByTitle(String query) {
        String needle = query == null ? "" : query.trim().toLowerCase();
        boolean found = false;
        for (LibraryItem item : catalog.values()) {
            if (item.getTitle().toLowerCase().contains(needle)) {
                item.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println(String.format("No items match %s.", query));        }
    }

    public void listAvailable() {
        boolean found = false;
        for (LibraryItem item : catalog.values()) {
            if (!item.isBorrowed()) {
                item.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items are currently available.");
        }
    }
    public Map<String, Integer> statisticsByType() {
        Map<String, Integer> byType = new HashMap<>();
        for (LibraryItem item : catalog.values()) {
            byType.merge(item.getType(), 1, Integer::sum);
        }
        return byType;
    }

}
