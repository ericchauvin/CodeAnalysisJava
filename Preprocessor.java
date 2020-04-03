// Copyright Eric Chauvin 2018 - 2020.


// Make a partial preprocessor and let
// gcc do the rest until I get the other parts
// working.



public class Preprocessor
  {

  public static String PreprocessFile( MainApp mApp,
                                     String fileName )
    {
    try
    {
    String showError = "";
    mApp.showStatus( "Preprocessing file:\n" + fileName );

    // The first level of lexical analysis and
    // processing is inside FileUtility.java when
    // it reads the file to a string.
    String result = FileUtility.readFileToString(
                                        mApp,
                                        fileName,
                                        false );

    if( result.trim().length() == 0 )
      {
      mApp.showStatus( "Nothing in Source File." );
      // Return an empty string to stop further 
      // processing.
      return "";
      }

    // This adds line number markers and also fixes
    // line splices.
    result = RemoveComments.removeAllComments(
                                      mApp, result );

    if( result.length() == 0 )
      return "";

    if( result.contains( Character.toString(
                      Markers.ErrorPoint )))
      {
      mApp.showStatus( " " );
      showError = "There was an error marker after" +
                               " RemoveAllComments.";

      mApp.showStatus( showError );
      return "";
      }

    if( !MarkupString.testBeginEnd( mApp, result ))
      {
      mApp.showStatus( " " );
      showError = "TestBeginEnd returned false" +
                        " after RemoveAllComments.";
      mApp.showStatus( showError );
      return "";
      }


    // This is created once for each new preprocessed
    // file.  So if I #include files I need to get
    // the defines from that other dictionary.
    MacroDictionary macroDict = new
                           MacroDictionary( mApp );
    
    // PreprocConditionals preprocCond = new
     //                 PreprocConditionals( mApp );
                                 // useDefinesDict );

    // result = preprocCond.mainFileLoop( result );

    if( result.length() == 0 )
      return "";



    // result = MarkupString.MarkItUp( mApp,
       //                                 result );


    // mApp.showStatus( result );
    mApp.showStatus( " " );
    // mApp.showStatus( "Done preprocessing:" );
    // mApp.showStatus( fileName );
    // mApp.showStatus( " " );

    return result;

    }
    catch( Exception e )
      {
      mApp.showStatus( "Exception in PreprocessFile()." );
      mApp.showStatus( e.getMessage() );
      return "";
      }
    }




  }
