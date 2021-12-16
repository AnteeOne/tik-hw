package tech.antee.cba.utils;

public class BinaryList {

    public class FourBits {

        public int d1;
        public int d2;
        public int d3;
        public int d4;

        public FourBits(int d1, int d2, int d3, int d4) {
            this.d1 = d1;
            this.d2 = d2;
            this.d3 = d3;
            this.d4 = d4;
        }

        public int getD1() {
            return d1;
        }

        public int getD2() {
            return d2;
        }

        public int getD3() {
            return d3;
        }

        public int getD4() {
            return d4;
        }
    }

    public static class SevenBits {

        int x1;int x2;int x3;int x4;int x5;int x6;int x7;

        public SevenBits(int x1, int x2, int x3, int x4, int x5, int x6, int x7) {
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
            this.x4 = x4;
            this.x5 = x5;
            this.x6 = x6;
            this.x7 = x7;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append(x1);
            result.append(x2);
            result.append(x3);
            result.append(x4);
            result.append(x5);
            result.append(x6);
            result.append(x7);
            return result.toString();
        }

        public String getDecoded() {
            int errorIndex = checkErrors();
            if(errorIndex != 0) {
                Log.m(Log.Tags.HAMMING,"Found error at " + errorIndex + " position in bits " + toString());
                solveError(errorIndex);
            }
            StringBuilder result = new StringBuilder();
            result.append(x3);
            result.append(x5);
            result.append(x6);
            result.append(x7);
            return result.toString();
        }

        public int checkErrors() {
            int p1 = (x3 + x5 + x7) % 2;
            int p2 = (x3 + x6 + x7) % 2;
            int p3 = (x5 + x6 + x7) % 2;

            int errorIndex = 0;
            if(p1 != x1) {
                errorIndex+=1;
            }
            if (p2 != x2) {
                errorIndex+=2;
            }
            if(p3 != x4 ) {
                errorIndex+=4;
            }
            return errorIndex;
        }

        public void solveError(int index) {
            switch (index) {
                case 1: {x1 = inverseBit(x1);break;}
                case 2: {x2 = inverseBit(x2);break;}
                case 3: {x3 = inverseBit(x3);break;}
                case 4: {x4 = inverseBit(x4);break;}
                case 5: {x5 = inverseBit(x5);break;}
                case 6: {x6 = inverseBit(x6);break;}
                case 7: {x7 = inverseBit(x7);break;}
            }
            Log.m(Log.Tags.HAMMING,"Solved error at " + index + " position to new bits " + toString());
        }

        public int inverseBit(int bit) {
            return (bit + 1) % 2;
        }
    }

    public byte[] arrayOfBytes;
    public int len;

    public byte[] powersOfTwo = new byte[] {
            (byte) 1,
            (byte) 2,
            (byte) 4,
            (byte) 8,
            (byte) 16,
            (byte) 32,
            (byte) 64,
            (byte) 128
    };

    public BinaryList(int len, byte[] arrayOfBytes) {
        this.len = len;
        this.arrayOfBytes = arrayOfBytes;
    }

    public BinaryList(int len) {
        this.len = len;
        int byteLength = len / 8;
        if (len % 8 != 0) {
            byteLength = byteLength + 1;
        }
        arrayOfBytes = new byte[byteLength];
    }

    public FourBits getFourBits(int index) {
        return new FourBits(
                getBitFromPosition(index),
                getBitFromPosition(index + 1),
                getBitFromPosition(index + 2),
                getBitFromPosition(index + 3)
        );
    }

    public int getBitFromPosition(int index) {
        int bitPosition = index % 8;
        int bytePosition = index / 8;
        if ((arrayOfBytes[bytePosition] & powersOfTwo[bitPosition]) != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setBitToPosition(int index, int value) {
        int byteIndex = index / 8;
        int bitIndex = index % 8;
        if (value != 0) {
            arrayOfBytes[byteIndex] = (byte) (arrayOfBytes[byteIndex] | powersOfTwo[bitIndex]);
        } else {
            arrayOfBytes[byteIndex] = (byte) (arrayOfBytes[byteIndex] & ~powersOfTwo[bitIndex]);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < len; i++) {

            result.append(getBitFromPosition(i));
        }
        return result.toString();
    }


    public int getLen() {
        return len;
    }

    public int getSizeInBytes() {
        return arrayOfBytes.length;
    }

}
