package edu.bu.ec504.hw1p1;
import java.util.* ;
import java.io.IOException;
import java.io.Reader;

public class myDistinctCounter extends DistinctCounter {
  static final double phi=0.775351;
  Byte[] state;
  /**
   * @inheritDoc
   */
  public myDistinctCounter(int stateSize) {
    super(stateSize);
    state = new Byte[stateSize];
  }

  /**
   * @inheritDoc
   */
  public myDistinctCounter(Byte[] initialState) {
    super(initialState);
    state = new Byte[initialState.length];
    System.arraycopy(initialState, 0, state, 0, initialState.length);
  }

  static int PositionRightmostSetbit(int n)
  {
    int position = 0;
    int m = 1;

    while ((n & m) == 0) {

      // left shift
      m = m << 1;
      position++;
    }
    return position;
  }


  void setbit(int pos, int bit,Byte[] st) {
    if (st[pos] == null)
      st[pos] = 0;
    st[pos] = (byte) (st[pos] | (1 << bit));
  }


  /**
   * @inheritDoc
   */
  @Override
  void saw(String newElement) {
    int num = newElement.hashCode();
    int pos = PositionRightmostSetbit(num);
    int posinstate = pos / 8;
    int posofbit = pos%8;
    setbit(posinstate,posofbit,state);
  }

  int hash1(String word) {
    int res = 0;
    for (int i =0; i< word.length() ;i ++) {
      res += 11*((int) word.charAt(i)) ;
    }
    return res;
  }

  /**
   * @inheritDoc
   */

   int findzero(Byte[] st) {
    int res = 0;
    for (int i = 0; i < st.length ; i ++ ) {
      if (st[i] == null)
        continue;
      for (int j = 0; j < 8; j++) {
        if ((st[i] & (1 << j)) == 0) {
          return res;
        }
        res ++;
      }
    }
    return 81;
  }

  @Override
  Integer numDistinct() {
    int pos = findzero(state);
    return (int) (Math.pow(2,pos)/phi);
  }

  public Byte[] currentState() {
    return state;
  }
}
