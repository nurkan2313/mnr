package kg.sh.mnr.model;

public class PageItem {
    private final int page;
    private final int label;

    public PageItem(int page) {
        this.page = page;
        this.label = page + 1;
    }

    public int getPage() {
        return page;
    }

    public int getLabel() {
        return label;
    }
}