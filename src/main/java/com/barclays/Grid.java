package com.barclays;

public interface Grid {

    boolean isInbound(Position position);

    boolean hasScentAt(Position position);

    void leaveScent(Position position);
}
