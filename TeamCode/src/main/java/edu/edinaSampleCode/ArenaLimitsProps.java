package edu.edinaSampleCode;

public enum ArenaLimitsProps {
    center,
    nearLeft {
        public int x() { return -72; }
        public int y() { return 0; }
    }
}
