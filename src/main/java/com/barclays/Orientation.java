package com.barclays;

public enum Orientation {


    N {
        @Override
        public Orientation turnRight() {
            return E;
        }

        @Override
        public Orientation turnLeft() {
            return W;
        }

        @Override
        public int deltaX() {
            return 0;
        }

        @Override
        public int deltaY() {
            return 1;
        }
    },
    E {
        @Override
        public Orientation turnRight() {
            return S;
        }

        @Override
        public Orientation turnLeft() {
            return N;
        }

        @Override
        public int deltaX() {
            return 1;
        }

        @Override
        public int deltaY() {
            return 0;
        }
    },
    S {
        @Override
        public Orientation turnRight() {
            return W;
        }

        @Override
        public Orientation turnLeft() {
            return E;
        }

        @Override
        public int deltaX() {
            return 0;
        }

        @Override
        public int deltaY() {
            return -1;
        }
    },
    W {
        @Override
        public Orientation turnRight() {
            return N;
        }

        @Override
        public Orientation turnLeft() {
            return S;
        }

        @Override
        public int deltaX() {
            return -1;
        }

        @Override
        public int deltaY() {
            return 0;
        }
    };

    public abstract Orientation turnRight();

    public abstract Orientation turnLeft();

    public abstract int deltaX();

    public abstract int deltaY();
}
