package com.maxcogito.collectapp.coinbasepro;

public class JsonStrArrayDesc {
    boolean valid = false;
    int innerArrayitems = 0;
    int stop_index= 0;
    int beg_outIndex = 0;
    int beg_innerIndex = 0;
    int length = 0;

    public int getStop_index() {
        return stop_index;
    }

    public void setStop_index(int stop_index) {
        this.stop_index = stop_index;
    }

    public int getEnd_outerArrayIndex() {
        return end_outerArrayIndex;
    }

    public void setEnd_outerArrayIndex(int end_outerArrayIndex) {
        this.end_outerArrayIndex = end_outerArrayIndex;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBeg_innerIndex() {
        return beg_innerIndex;
    }

    public void setBeg_innerIndex(int beg_innerIndex) {
        this.beg_innerIndex = beg_innerIndex;
    }

    public int getBeg_outIndex() {
        return beg_outIndex;
    }

    public void setBeg_outIndex(int beg_outIndex) {
        this.beg_outIndex = beg_outIndex;
    }

    public int getInnerArrayitems() {
        return innerArrayitems;
    }

    public void setInnerArrayitems(int innerArrayitems) {
        this.innerArrayitems = innerArrayitems;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    int end_outerArrayIndex = 0;
}
