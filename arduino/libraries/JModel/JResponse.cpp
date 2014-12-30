#ifndef JRESPONSE_CPP
#define JRESPONSE_CPP

#include "JResponse.h"


JResponse::JResponse(char resType)
{
    header[0] = 'J'; header[1] = 'R'; header[2] = 'E'; header[3] = 'S';
    footer[0] = '<'; footer[1] = '-'; footer[2] = '-';
    
    responseType = resType;
}

JResponse::JResponse()
{
    header[0] = 'J'; header[1] = 'R'; header[2] = 'E'; header[3] = 'S';
    footer[0] = '<'; footer[1] = '-'; footer[2] = '-';    
}

#endif