#include <statistika.h>

JNIEXPORT jint JNICALL Java_ra241_12015_pnrs1_rtrk_taskmanager_StatistikaNative_izracunajStatistiku
  (JNIEnv *env, jobject obj, jint ukupanBr, jint zavrsenBr)
  {
	
	  if (ukupanBr == 0) 
	  {
            zavrsenBr = 0;
			
			return 0;
      } 
	  else
   
	 return (jint) floor(zavrsenBr * 100 / ukupanBr);
  }