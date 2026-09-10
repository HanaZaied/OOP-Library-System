import java.util.List;

public class Member {
    /**
     * String memberId
     * • String name
     * • int maxAllowed – the most items this member may hold at once (e.g. 3)
     * • List<LibraryItem> borrowedItems – note the list type is the base class, so a member can hold
     * Books, Magazines, and DVDs together
     */

    private String memberId;
    private String name;
    private int maxAllowed;
    private List<LibraryItem> borrowedItems;


    public Member(String memberId, String name, int maxAllowed) {
        setMemberId(memberId);
        setName(name);
        setMaxAllowed(maxAllowed);
        this.borrowedItems = borrowedItems;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member id cannot be null or empty.");
        }
        this.memberId = memberId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be empty.");
        }
        this.name = name.trim();
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }

    public void setMaxAllowed(int maxAllowed) {
        if (maxAllowed <= 0) {
            throw new IllegalArgumentException("maxAllowed must be a positive number.");
        }
        this.maxAllowed = maxAllowed;
    }

    public List<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }

    public int getBorrowedCount() {
        return borrowedItems.size();
    }

    public boolean canBorrowMore() {
        return borrowedItems.size() < maxAllowed;
    }

    void addBorrowedItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    boolean removeBorrowedItem(LibraryItem item) {
        return borrowedItems.remove(item);
    }

    boolean isHolding(LibraryItem item) {
        return borrowedItems.contains(item);
    }

}
