// Copyright Eric Chauvin 2020.



public class StrArray
  {
  private StrA[] valueArray;
  private int[] sortIndexArray;
  private int arrayLast = 0;
  private StrABld sBld;


  public StrArray()
    {
    sBld = new StrABld( 1024 * 64 );
    valueArray = new StrA[8];
    sortIndexArray = new int[8];
    resetSortIndexArray();
    }


  public void clear()
    {
    arrayLast = 0;
    }



  public int length()
    {
    return arrayLast;
    }


  private void resetSortIndexArray()
    {
    // It's not to arrayLast.  It's to the whole length.
    int max = sortIndexArray.length;
    for( int count = 0; count < max; count++ )
      sortIndexArray[count] = count;

    }



  private void resizeArrays( int toAdd )
    {
    int oldLength = sortIndexArray.length;
    sortIndexArray = new int[oldLength + toAdd];
    resetSortIndexArray();

    StrA[] tempValueArray = new StrA[oldLength + toAdd];
    for( int count = 0; count < arrayLast; count++ )
      {
      tempValueArray[count] = valueArray[count];
      }

    valueArray = tempValueArray;
    }



  public void sort()
    {
    if( arrayLast < 2 )
      return;

    for( int count = 0; count < arrayLast; count++ )
      {
      if( !bubbleSortOnePass() )
        break;

      }
    }



  private boolean bubbleSortOnePass()
    {
    // This returns true if it swaps anything.

    boolean switched = false;
    for( int count = 0; count < (arrayLast - 1); count++ )
      {
      // compareTo() uses case.
      if( valueArray[count].compareToIgnoreCase(
                        valueArray[count + 1] ) > 0 )
        {
        int temp = sortIndexArray[count];
        sortIndexArray[count] = sortIndexArray[count + 1];
        sortIndexArray[count + 1] = temp;
        switched = true;
        }
      }

    return switched;
    }



  public void append( StrA value )
    {
    if( arrayLast >= sortIndexArray.length )
      resizeArrays( 1024 * 64 );

    valueArray[arrayLast] = value;
    arrayLast++;
    }



  public int makeFieldsFromStrA( StrA in,
                                   char delimit )
    {
    clear();
    sBld.setLength( 0 );

    if( in == null )
      return 0;

    int max = in.length();
    if( max == 0 )
      return 0;

    for( int count = 0; count < max; count++ )
      {
      char testChar = in.charAt( count );
      if( testChar == delimit )
        {
        append( sBld.toStrA());
        sBld.setLength( 0 );
        }
      else
        {
        sBld.appendChar( testChar );
        }
      }

    if( sBld.length() > 0 )
      {
      append( sBld.toStrA());
      }

    return arrayLast;
    }


  public StrA getStrAt( int where )
    {
    if( where < 0 )
      return new StrA( "" );

    if( where >= arrayLast )
      return new StrA( "" );

    return valueArray[where];
    }


  }
