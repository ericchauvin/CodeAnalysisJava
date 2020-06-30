// Copyright Eric Chauvin 2020.



public class PreprocessProject
  {
  private MainApp mApp;
  private Thread fileThread;



  private PreprocessProject()
    {
    }



  public PreprocessProject( MainApp useApp )
    {
    mApp = useApp;
    }



  public void cancel()
    {
    if( fileThread != null )
      {
      mApp.showStatusAsync( "Canceling..." );
      fileThread.interrupt();
      fileThread = null;
      }
    }



  public void doProject()
    {
    testFiles();
    // listHeaderFiles();
    }



  private void testFiles()
    {
    try
    {
    // String fileName = "\\jdk7hotspotmaster\\src\\share\\tools\\launcher\\java.c";
    // String fileName = "\\Eric\\CodeAnalysisCpp\\main.cpp";

    // String mainDir = "C:\\jdk7hotspotmaster\\src\\share\\vm\\code\\";
    // String mainDir = "C:\\gccmaster\\gcc\\";
    String outDir = "C:\\PreprocessOut\\";

    StrA projectFileName = new StrA( 
          "\\Eric\\CPreprocessor\\ProjectFiles.txt" );

    StrA headerFileName = new StrA(  
           "\\Eric\\CPreprocessor\\HeaderFiles.txt" );

    HeaderFileDictionary headerDictionary = new
                        HeaderFileDictionary( mApp );

    headerDictionary.readFile( headerFileName );


    StrA fileS = FileUtility.readFileToStrA(
                                      mApp,
                                      projectFileName,
                                      false,
                                      false );

    if( fileS.length() == 0 )
      {
      mApp.showStatusAsync( "Nothing in the project file." );
      return;
      }


    // .rc extension
    // Resource files for Windows.  Like bitmaps,
    // menus, icons, etc.

    StrArray fileArray = fileS.splitChar( '\n' );
    final int max = fileArray.length();
    for( int count = 0; count < max; count++ )
      {
      // For testing:
      // if( count > 2 )
        // break;

      StrA fileName = fileArray.getStrAt( count );
      fileName = fileName.trim();
      if( fileName.length() < 1 )
        continue;

      if( fileName.startsWith( new StrA( "//" )))
        continue;

      StrA outFileName = new StrA( outDir );
      outFileName.concat( Utility.getFileName(
                          fileName, '\\' ));

      mApp.showStatusAsync( "Out file: " + outFileName );
    
      MacroDictionary macroDictionary = new
                           MacroDictionary( mApp );

      addMacros( macroDictionary );

      StrA result = Preprocessor.PreprocessFile(
                                 mApp,
                                 fileName,
                                 macroDictionary,
                                 headerDictionary );

      if( result.length() == 0 )
        {
        mApp.showStatusAsync( " " );
        mApp.showStatusAsync( "The file had an error." );
        mApp.showStatusAsync( fileName.toString() );
        headerDictionary.writeFile( headerFileName );
        return;
        }

      FileUtility.writeStrAToFile( mApp,
                               outFileName,
                               result,
                               false,
                               false );

      }

    headerDictionary.writeFile( headerFileName );

    mApp.showStatusAsync( " " );
    mApp.showStatusAsync( "Finished processing files." );
    mApp.showStatusAsync( " " );
    }
    catch( Exception e )
      {
      mApp.showStatusAsync( "Exception in testFiles()." );
      mApp.showStatusAsync( e.getMessage() );
      }
    }




  private void addMacros( MacroDictionary
                          macroDictionary )
    {
    // Make a configuration file for these.

/*
Are these Cygwin predefined macros?

#define __DBL_MIN_EXP__ (-1021)
#define __FLT_MIN__ 1.17549435e-38F
#define __CHAR_BIT__ 8
#define __WCHAR_MAX__ 65535U
#define __DBL_DENORM_MIN__ 4.9406564584124654e-324
#define __FLT_EVAL_METHOD__ 2
#define __DBL_MIN_10_EXP__ (-307)
#define __FINITE_MATH_ONLY__ 0
#define __GNUC_PATCHLEVEL__ 4
#define _stdcall __attribute__((__stdcall__))
#define __SHRT_MAX__ 32767
#define __LDBL_MAX__ 1.18973149535723176502e+4932L
#define __unix 1
#define __LDBL_MAX_EXP__ 16384
#define __SCHAR_MAX__ 127
#define __USER_LABEL_PREFIX__ _
#define __STDC_HOSTED__ 1
#define __LDBL_HAS_INFINITY__ 1
#define __DBL_DIG__ 15
#define __FLT_EPSILON__ 1.19209290e-7F
#define __tune_i686__ 1
#define __LDBL_MIN__ 3.36210314311209350626e-4932L
#define __unix__ 1
#define __DECIMAL_DIG__ 21
#define __LDBL_HAS_QUIET_NAN__ 1
#define __GNUC__ 3
#define _cdecl __attribute__((__cdecl__))
#define __DBL_MAX__ 1.7976931348623157e+308
#define __DBL_HAS_INFINITY__ 1
#define _fastcall __attribute__((__fastcall__))
#define __USING_SJLJ_EXCEPTIONS__ 1
#define __DBL_MAX_EXP__ 1024
#define __LONG_LONG_MAX__ 9223372036854775807LL
#define __GXX_ABI_VERSION 1002
#define __FLT_MIN_EXP__ (-125)
#define __DBL_MIN__ 2.2250738585072014e-308
#define __DBL_HAS_QUIET_NAN__ 1
#define __REGISTER_PREFIX__ 
#define __cdecl __attribute__((__cdecl__))
#define __NO_INLINE__ 1
#define __i386 1
#define __FLT_MANT_DIG__ 24
#define __VERSION__ "3.4.4 (cygming special, gdc 0.12, using dmd 0.125)"
#define _X86_ 1
#define i386 1
#define unix 1
#define __i386__ 1
#define __SIZE_TYPE__ unsigned int
#define __FLT_RADIX__ 2
#define __LDBL_EPSILON__ 1.08420217248550443401e-19L
#define __CYGWIN__ 1
#define __FLT_HAS_QUIET_NAN__ 1
#define __FLT_MAX_10_EXP__ 38
#define __LONG_MAX__ 2147483647L
#define __FLT_HAS_INFINITY__ 1
#define __stdcall __attribute__((__stdcall__))
#define __LDBL_MANT_DIG__ 64
#define __WCHAR_TYPE__ short unsigned int
#define __FLT_DIG__ 6
#define __INT_MAX__ 2147483647
#define __FLT_MAX_EXP__ 128
#define __DBL_MANT_DIG__ 53
#define __WINT_TYPE__ unsigned int
#define __LDBL_MIN_EXP__ (-16381)
#define __LDBL_MAX_10_EXP__ 4932
#define __DBL_EPSILON__ 2.2204460492503131e-16
#define __tune_pentiumpro__ 1
#define __fastcall __attribute__((__fastcall__))
#define __CYGWIN32__ 1
#define __FLT_DENORM_MIN__ 1.40129846e-45F
#define __FLT_MAX__ 3.40282347e+38F
#define __FLT_MIN_10_EXP__ (-37)
#define __GNUC_MINOR__ 4
#define __DBL_MAX_10_EXP__ 308
#define __LDBL_DENORM_MIN__ 3.64519953188247460253e-4951L
#define __PTRDIFF_TYPE__ int
#define __LDBL_MIN_10_EXP__ (-4931)
#define __LDBL_DIG__ 18
#define __declspec(x) __attribute__((x))
*/

    // Target Architechture.
    // "Zero-Assembler Project: Zero is a port of
    // OpenJDK that uses no assembler and therefore
    // can trivially be built on any system."

    // TARGET_ARCH_zero, TARGET_ARCH_arm,
    Macro macro = new Macro( mApp );
    macro.setMacroWithEmptyParams( new StrA( 
                     "TARGET_ARCH_x86" ));
    macroDictionary.setMacro( "TARGET_ARCH_x86",
                                macro );


    // Intel x86 is Little Endian.
    // __IEEE_BIG_ENDIAN
    macro = new Macro( mApp );
    macro.setMacroWithEmptyParams( new StrA(
                          "__IEEE_LITTLE_ENDIAN" ));
    macroDictionary.setMacro( "__IEEE_LITTLE_ENDIAN",
                                macro );

    // I want to use the Cygwin compiler to compile
    // this after it is preprocessed here.
    macro = new Macro( mApp );
    macro.setMacroWithEmptyParams( new StrA(
                                     "__CYGWIN__" ));
    macroDictionary.setMacro( "__CYGWIN__",
                                macro );


    // Gamma is Hotspot internal test code.
    // Leave GAMMA undefined.

 
    // __cplusplus
     
    macro = new Macro( mApp );
    macro.setMacroWithEmptyParams( new StrA(
                                   "__x86_64__" ));
    macroDictionary.setMacro( "__x86_64__",
                                macro );

    macro = new Macro( mApp );
    macro.setMacroWithEmptyParams( new StrA(
                                "__STRICT_ANSI__" ));
    macroDictionary.setMacro( "__STRICT_ANSI__",
                                macro );

    // JIT compilers.    
    // C1 compiler.  Bytecode compiler.
    // C2 compiler.  Called opto?  Higher
    // optimization.

    macro = new Macro( mApp );
    macro.setMacroWithEmptyParams( new StrA(
                                     "COMPILER1" ));
    macroDictionary.setMacro( "COMPILER1", macro );

    // .pch is Precompiled header.
    // or  .gch for Gnu.  Gnu compiled header.
    }



  private void listHeaderFiles()
    {
    // String dir = "\\jdk7hotspotmaster\\src";
    // String dir = "\\cygwin64\\usr\\include";
    // String dir = "\\cygwin64\\usr\\include\\sys";
    // String dir = "\\cygwin64\\usr\\include\\machine";
    // String dir = "\\cygwin64\\usr\\include\\bits";
    // String dir = "\\cygwin64\\usr\\include\\c++";
    // String dir = "\\cygwin64\\usr\\include\\cygwin";
    // String dir = "\\cygwin-master\\winsup\\cygwin";

    // String dir = "\\EricFiles\\WindowsSdk10\\ucrt";
    // String dir = "\\EricFiles\\WindowsSdk10\\um";
    String dir = "\\EricFiles\\WindowsSdk10\\shared";


    // Add it to the semicolon delimited dictionary file.
    // endsWith( fileToFind )
    String fileToFind = ""; // stddef.h";
    listFiles( dir, fileToFind );
    }



  private void listFiles( String dir,
                          String fileToFind )
    {
    try
    {
    if( fileThread != null )
      {
      // If it's not still doing something.
      if( !fileThread.isAlive())
        {
        fileThread = null;
        }
      else
        {
        mApp.showStatusAsync( "The thread is already running." );
        return;
        }
      }

// fileToFind
    FileSearchRunnable fileSearch = new 
                       FileSearchRunnable( mApp,
                       dir,
                       true,
                       "" );

    fileThread = new Thread( fileSearch );
    fileThread.start();
    }
    catch( Exception e )
      {
      mApp.showStatusAsync( "Exception in listFiles()." );
      mApp.showStatusAsync( e.getMessage() );
      }
    }



  }
