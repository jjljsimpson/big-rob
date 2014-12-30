#ifndef COMMANDTYPE_H_
#define COMMANDTYPE_H_


#define COMMAND_SET_PIN_TYPE 	'p' /* set how the pin will be used */
#define COMMAND_SET_PIN_VALUE 	'v' /* set the value for a pin */
#define COMMAND_SET_COMPASS		'c' /* set the pin as a compass type*/
#define COMMAND_SET_PING		'i' /* set the pin as a ultrasonic type*/
#define COMMAND_SET_SERVO		's' /* set the pin as a servo type */
#define COMMAND_WAIT 			'w' /* how long to wait before processing the next command. This only works for test commands */
#define COMMAND_GET_PIN_VALUE 	'r' /* arduino sends back value from a pin */


#endif
