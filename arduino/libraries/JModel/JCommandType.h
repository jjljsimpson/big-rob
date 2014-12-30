#ifndef JCOMMANDTYPE_H
#define JCOMMANDTYPE_H

/* Commands */
#define COMMAND_SET_PIN_TYPE        0   /* set how the pin will be used */
#define COMMAND_SET_PIN_VALUE       1   /* set the value for a pin */
#define COMMAND_SET_PIN_UPDATE      2   /* set how often the pin should be set / read */
#define COMMAND_WAIT                3   /* how long to wait before processing the next command. This only works for test commands */
#define COMMAND_INFO                4   /* arduino needs to send back info about itself */
#define COMMAND_GET_PIN_VALUE       5   /* arduino sends back value from a pin */

/* Command Type */
#define COMMAND_TYPE_REGULAR        0   /* Regular command (ignores wait command) */
#define COMMAND_TYPE_TEST           1   /* Test command (allows wait command) */



#endif