/*
 * Very simple (yet, for some reason, very effective) memory tester.
 * Originally by Simon Kirby <sim@stormix.com> <sim@neato.org>
 * Version 2 by Charles Cazabon <charlesc-memtester@pyropus.ca>
 * Version 3 not publicly released.
 * Version 4 rewrite:
 * Copyright (C) 2004-2012 Charles Cazabon <charlesc-memtester@pyropus.ca>
 * Licensed under the terms of the GNU General Public License version 2 (only).
 * See the file COPYING for details.
 *
 * This file contains the declarations for external variables from the main file.
 * See other comments in that file.
 *
 */
#ifndef MEMTESTER_H
#define MEMTESTER_H
#include <sys/types.h>
#include "types.h"

/* extern declarations. */
#define TESTS_SIZE 16
extern int use_phys;
extern off_t physaddrbase;
extern struct test tests[TESTS_SIZE];

void tests_report_progress(float progress);
int my_fprintf(FILE *fp, const char *fmt, ...);
#endif
