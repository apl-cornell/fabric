package fabnfs;
//
// Copyright 1997, 1998 Steven James Procter
// All rights under copyright reserved.
//  
// Everyone is granted permission to copy, modify and redistribute this code.
// Modification or redistribution for profit is expressely disallowed.
// The copyright notice and this notice must be preserved on all copies.
// 
// This software is distributed as-is.  No warranty of merchantability or
// fitness for any purpose is stated or implied.
// 
// It is my intention that anyone be able to use this software but not
// incorporate it or any part of it into any product or any software which
// is sold without first obtaining a licensing agreement from me the author.
// 
// 							Steven James Procter
// 							steven@void.org
// 							March 1997
//

//
// Time mapper for the java3 platform.  Java 3 times are in milliseconds
//   since 1970.
//

class Java3TimeMapper extends TimeMapper {
    long Seconds(long localTime) {
	return localTime / 1000;
    }
    long MilliSeconds(long localTime) {
	return localTime % 1000;
    }
};
