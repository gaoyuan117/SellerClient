/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaichaohulian.baocms.ecdemo.ui.chatting.base;

import android.content.Context;
import android.text.Spannable;
import android.util.SparseIntArray;

import com.kaichaohulian.baocms.R;

public final class EmojiconHandler {
    private EmojiconHandler() {
    }

    private static final SparseIntArray sEmojisMap = new SparseIntArray(846);
    private static final SparseIntArray sSoftbanksMap = new SparseIntArray(471);

    static {
        // People
        sEmojisMap.put(0x1f604, R.mipmap.emoji_1f604);
        sEmojisMap.put(0x1f603, R.mipmap.emoji_1f603);
        sEmojisMap.put(0x1f600, R.mipmap.emoji_1f600);
        sEmojisMap.put(0x1f60a, R.mipmap.emoji_1f60a);
        sEmojisMap.put(0x263a, R.mipmap.emoji_263a);
        sEmojisMap.put(0x1f609, R.mipmap.emoji_1f609);
        sEmojisMap.put(0x1f60d, R.mipmap.emoji_1f60d);
        sEmojisMap.put(0x1f618, R.mipmap.emoji_1f618);
        sEmojisMap.put(0x1f61a, R.mipmap.emoji_1f61a);
        sEmojisMap.put(0x1f617, R.mipmap.emoji_1f617);
        sEmojisMap.put(0x1f619, R.mipmap.emoji_1f619);
        sEmojisMap.put(0x1f61c, R.mipmap.emoji_1f61c);
        sEmojisMap.put(0x1f61d, R.mipmap.emoji_1f61d);
        sEmojisMap.put(0x1f61b, R.mipmap.emoji_1f61b);
        sEmojisMap.put(0x1f633, R.mipmap.emoji_1f633);
        sEmojisMap.put(0x1f601, R.mipmap.emoji_1f601);
        sEmojisMap.put(0x1f614, R.mipmap.emoji_1f614);
        sEmojisMap.put(0x1f60c, R.mipmap.emoji_1f60c);
        sEmojisMap.put(0x1f612, R.mipmap.emoji_1f612);
        sEmojisMap.put(0x1f61e, R.mipmap.emoji_1f61e);
        sEmojisMap.put(0x1f623, R.mipmap.emoji_1f623);
        sEmojisMap.put(0x1f622, R.mipmap.emoji_1f622);
        sEmojisMap.put(0x1f602, R.mipmap.emoji_1f602);
        sEmojisMap.put(0x1f62d, R.mipmap.emoji_1f62d);
        sEmojisMap.put(0x1f62a, R.mipmap.emoji_1f62a);
        sEmojisMap.put(0x1f625, R.mipmap.emoji_1f625);
        sEmojisMap.put(0x1f630, R.mipmap.emoji_1f630);
        sEmojisMap.put(0x1f605, R.mipmap.emoji_1f605);
        sEmojisMap.put(0x1f613, R.mipmap.emoji_1f613);
        sEmojisMap.put(0x1f629, R.mipmap.emoji_1f629);
        sEmojisMap.put(0x1f62b, R.mipmap.emoji_1f62b);
        sEmojisMap.put(0x1f628, R.mipmap.emoji_1f628);
        sEmojisMap.put(0x1f631, R.mipmap.emoji_1f631);
        sEmojisMap.put(0x1f620, R.mipmap.emoji_1f620);
        sEmojisMap.put(0x1f621, R.mipmap.emoji_1f621);
        sEmojisMap.put(0x1f624, R.mipmap.emoji_1f624);
        sEmojisMap.put(0x1f616, R.mipmap.emoji_1f616);
        sEmojisMap.put(0x1f606, R.mipmap.emoji_1f606);
        sEmojisMap.put(0x1f60b, R.mipmap.emoji_1f60b);
        sEmojisMap.put(0x1f637, R.mipmap.emoji_1f637);
        sEmojisMap.put(0x1f60e, R.mipmap.emoji_1f60e);
        sEmojisMap.put(0x1f634, R.mipmap.emoji_1f634);
        sEmojisMap.put(0x1f635, R.mipmap.emoji_1f635);
        sEmojisMap.put(0x1f632, R.mipmap.emoji_1f632);
        sEmojisMap.put(0x1f61f, R.mipmap.emoji_1f61f);
        sEmojisMap.put(0x1f626, R.mipmap.emoji_1f626);
        sEmojisMap.put(0x1f627, R.mipmap.emoji_1f627);
        sEmojisMap.put(0x1f608, R.mipmap.emoji_1f608);
        sEmojisMap.put(0x1f47f, R.mipmap.emoji_1f47f);
        sEmojisMap.put(0x1f62e, R.mipmap.emoji_1f62e);
        sEmojisMap.put(0x1f62c, R.mipmap.emoji_1f62c);
        sEmojisMap.put(0x1f610, R.mipmap.emoji_1f610);
        sEmojisMap.put(0x1f615, R.mipmap.emoji_1f615);
        sEmojisMap.put(0x1f62f, R.mipmap.emoji_1f62f);
        sEmojisMap.put(0x1f636, R.mipmap.emoji_1f636);
        sEmojisMap.put(0x1f607, R.mipmap.emoji_1f607);
        sEmojisMap.put(0x1f60f, R.mipmap.emoji_1f60f);
        sEmojisMap.put(0x1f611, R.mipmap.emoji_1f611);
        sEmojisMap.put(0x1f472, R.mipmap.emoji_1f472);
        sEmojisMap.put(0x1f473, R.mipmap.emoji_1f473);
        sEmojisMap.put(0x1f46e, R.mipmap.emoji_1f46e);
        sEmojisMap.put(0x1f477, R.mipmap.emoji_1f477);
        sEmojisMap.put(0x1f482, R.mipmap.emoji_1f482);
        sEmojisMap.put(0x1f476, R.mipmap.emoji_1f476);
        sEmojisMap.put(0x1f466, R.mipmap.emoji_1f466);
        sEmojisMap.put(0x1f467, R.mipmap.emoji_1f467);
        sEmojisMap.put(0x1f468, R.mipmap.emoji_1f468);
        sEmojisMap.put(0x1f469, R.mipmap.emoji_1f469);
        sEmojisMap.put(0x1f474, R.mipmap.emoji_1f474);
        sEmojisMap.put(0x1f475, R.mipmap.emoji_1f475);
        sEmojisMap.put(0x1f471, R.mipmap.emoji_1f471);
        sEmojisMap.put(0x1f47c, R.mipmap.emoji_1f47c);
        sEmojisMap.put(0x1f478, R.mipmap.emoji_1f478);
        sEmojisMap.put(0x1f63a, R.mipmap.emoji_1f63a);
        sEmojisMap.put(0x1f638, R.mipmap.emoji_1f638);
        sEmojisMap.put(0x1f63b, R.mipmap.emoji_1f63b);
        sEmojisMap.put(0x1f63d, R.mipmap.emoji_1f63d);
        sEmojisMap.put(0x1f63c, R.mipmap.emoji_1f63c);
        sEmojisMap.put(0x1f640, R.mipmap.emoji_1f640);
        sEmojisMap.put(0x1f63f, R.mipmap.emoji_1f63f);
        sEmojisMap.put(0x1f639, R.mipmap.emoji_1f639);
        sEmojisMap.put(0x1f63e, R.mipmap.emoji_1f63e);
        sEmojisMap.put(0x1f479, R.mipmap.emoji_1f479);
        sEmojisMap.put(0x1f47a, R.mipmap.emoji_1f47a);
        sEmojisMap.put(0x1f648, R.mipmap.emoji_1f648);
        sEmojisMap.put(0x1f649, R.mipmap.emoji_1f649);
        sEmojisMap.put(0x1f64a, R.mipmap.emoji_1f64a);
        sEmojisMap.put(0x1f480, R.mipmap.emoji_1f480);
        sEmojisMap.put(0x1f47d, R.mipmap.emoji_1f47d);
        sEmojisMap.put(0x1f4a9, R.mipmap.emoji_1f4a9);
        sEmojisMap.put(0x1f525, R.mipmap.emoji_1f525);
        sEmojisMap.put(0x2728, R.mipmap.emoji_2728);
        sEmojisMap.put(0x1f31f, R.mipmap.emoji_1f31f);
        sEmojisMap.put(0x1f4ab, R.mipmap.emoji_1f4ab);
        sEmojisMap.put(0x1f4a5, R.mipmap.emoji_1f4a5);
        sEmojisMap.put(0x1f4a2, R.mipmap.emoji_1f4a2);
        sEmojisMap.put(0x1f4a6, R.mipmap.emoji_1f4a6);
        sEmojisMap.put(0x1f4a7, R.mipmap.emoji_1f4a7);
        sEmojisMap.put(0x1f4a4, R.mipmap.emoji_1f4a4);
        sEmojisMap.put(0x1f4a8, R.mipmap.emoji_1f4a8);
        sEmojisMap.put(0x1f442, R.mipmap.emoji_1f442);
        sEmojisMap.put(0x1f440, R.mipmap.emoji_1f440);
        sEmojisMap.put(0x1f443, R.mipmap.emoji_1f443);
        sEmojisMap.put(0x1f445, R.mipmap.emoji_1f445);
        sEmojisMap.put(0x1f444, R.mipmap.emoji_1f444);
        sEmojisMap.put(0x1f44d, R.mipmap.emoji_1f44d);
        sEmojisMap.put(0x1f44e, R.mipmap.emoji_1f44e);
        sEmojisMap.put(0x1f44c, R.mipmap.emoji_1f44c);
        sEmojisMap.put(0x1f44a, R.mipmap.emoji_1f44a);
        sEmojisMap.put(0x270a, R.mipmap.emoji_270a);
        sEmojisMap.put(0x270c, R.mipmap.emoji_270c);
        sEmojisMap.put(0x1f44b, R.mipmap.emoji_1f44b);
        sEmojisMap.put(0x270b, R.mipmap.emoji_270b);
        sEmojisMap.put(0x1f450, R.mipmap.emoji_1f450);
        sEmojisMap.put(0x1f446, R.mipmap.emoji_1f446);
        sEmojisMap.put(0x1f447, R.mipmap.emoji_1f447);
        sEmojisMap.put(0x1f449, R.mipmap.emoji_1f449);
        sEmojisMap.put(0x1f448, R.mipmap.emoji_1f448);
        sEmojisMap.put(0x1f64c, R.mipmap.emoji_1f64c);
        sEmojisMap.put(0x1f64f, R.mipmap.emoji_1f64f);
        sEmojisMap.put(0x261d, R.mipmap.emoji_261d);
        sEmojisMap.put(0x1f44f, R.mipmap.emoji_1f44f);
        sEmojisMap.put(0x1f4aa, R.mipmap.emoji_1f4aa);
        sEmojisMap.put(0x1f6b6, R.mipmap.emoji_1f6b6);
        sEmojisMap.put(0x1f3c3, R.mipmap.emoji_1f3c3);
        sEmojisMap.put(0x1f483, R.mipmap.emoji_1f483);
        sEmojisMap.put(0x1f46b, R.mipmap.emoji_1f46b);
        sEmojisMap.put(0x1f46a, R.mipmap.emoji_1f46a);
        sEmojisMap.put(0x1f46c, R.mipmap.emoji_1f46c);
        sEmojisMap.put(0x1f46d, R.mipmap.emoji_1f46d);
        sEmojisMap.put(0x1f48f, R.mipmap.emoji_1f48f);
        sEmojisMap.put(0x1f491, R.mipmap.emoji_1f491);
        sEmojisMap.put(0x1f46f, R.mipmap.emoji_1f46f);
        sEmojisMap.put(0x1f646, R.mipmap.emoji_1f646);
        sEmojisMap.put(0x1f645, R.mipmap.emoji_1f645);
        sEmojisMap.put(0x1f481, R.mipmap.emoji_1f481);
        sEmojisMap.put(0x1f64b, R.mipmap.emoji_1f64b);
        sEmojisMap.put(0x1f486, R.mipmap.emoji_1f486);
        sEmojisMap.put(0x1f487, R.mipmap.emoji_1f487);
        sEmojisMap.put(0x1f485, R.mipmap.emoji_1f485);
        sEmojisMap.put(0x1f470, R.mipmap.emoji_1f470);
        sEmojisMap.put(0x1f64e, R.mipmap.emoji_1f64e);
        sEmojisMap.put(0x1f64d, R.mipmap.emoji_1f64d);
        sEmojisMap.put(0x1f647, R.mipmap.emoji_1f647);
        sEmojisMap.put(0x1f3a9, R.mipmap.emoji_1f3a9);
        sEmojisMap.put(0x1f451, R.mipmap.emoji_1f451);
        sEmojisMap.put(0x1f452, R.mipmap.emoji_1f452);
        sEmojisMap.put(0x1f45f, R.mipmap.emoji_1f45f);
        sEmojisMap.put(0x1f45e, R.mipmap.emoji_1f45e);
        sEmojisMap.put(0x1f461, R.mipmap.emoji_1f461);
        sEmojisMap.put(0x1f460, R.mipmap.emoji_1f460);
        sEmojisMap.put(0x1f462, R.mipmap.emoji_1f462);
        sEmojisMap.put(0x1f455, R.mipmap.emoji_1f455);
        sEmojisMap.put(0x1f454, R.mipmap.emoji_1f454);
        sEmojisMap.put(0x1f45a, R.mipmap.emoji_1f45a);
        sEmojisMap.put(0x1f457, R.mipmap.emoji_1f457);
        sEmojisMap.put(0x1f3bd, R.mipmap.emoji_1f3bd);
        sEmojisMap.put(0x1f456, R.mipmap.emoji_1f456);
        sEmojisMap.put(0x1f458, R.mipmap.emoji_1f458);
        sEmojisMap.put(0x1f459, R.mipmap.emoji_1f459);
        sEmojisMap.put(0x1f4bc, R.mipmap.emoji_1f4bc);
        sEmojisMap.put(0x1f45c, R.mipmap.emoji_1f45c);
        sEmojisMap.put(0x1f45d, R.mipmap.emoji_1f45d);
        sEmojisMap.put(0x1f45b, R.mipmap.emoji_1f45b);
        sEmojisMap.put(0x1f453, R.mipmap.emoji_1f453);
        sEmojisMap.put(0x1f380, R.mipmap.emoji_1f380);
        sEmojisMap.put(0x1f302, R.mipmap.emoji_1f302);
        sEmojisMap.put(0x1f484, R.mipmap.emoji_1f484);
        sEmojisMap.put(0x1f49b, R.mipmap.emoji_1f49b);
        sEmojisMap.put(0x1f499, R.mipmap.emoji_1f499);
        sEmojisMap.put(0x1f49c, R.mipmap.emoji_1f49c);
        sEmojisMap.put(0x1f49a, R.mipmap.emoji_1f49a);
        sEmojisMap.put(0x2764, R.mipmap.emoji_2764);
        sEmojisMap.put(0x1f494, R.mipmap.emoji_1f494);
        sEmojisMap.put(0x1f497, R.mipmap.emoji_1f497);
        sEmojisMap.put(0x1f493, R.mipmap.emoji_1f493);
        sEmojisMap.put(0x1f495, R.mipmap.emoji_1f495);
        sEmojisMap.put(0x1f496, R.mipmap.emoji_1f496);
        sEmojisMap.put(0x1f49e, R.mipmap.emoji_1f49e);
        sEmojisMap.put(0x1f498, R.mipmap.emoji_1f498);
        sEmojisMap.put(0x1f48c, R.mipmap.emoji_1f48c);
        sEmojisMap.put(0x1f48b, R.mipmap.emoji_1f48b);
        sEmojisMap.put(0x1f48d, R.mipmap.emoji_1f48d);
        sEmojisMap.put(0x1f48e, R.mipmap.emoji_1f48e);
        sEmojisMap.put(0x1f464, R.mipmap.emoji_1f464);
        sEmojisMap.put(0x1f465, R.mipmap.emoji_1f465);
        sEmojisMap.put(0x1f4ac, R.mipmap.emoji_1f4ac);
        sEmojisMap.put(0x1f463, R.mipmap.emoji_1f463);
        sEmojisMap.put(0x1f4ad, R.mipmap.emoji_1f4ad);

        // Nature
        sEmojisMap.put(0x1f436, R.mipmap.emoji_1f436);
        sEmojisMap.put(0x1f43a, R.mipmap.emoji_1f43a);
        sEmojisMap.put(0x1f431, R.mipmap.emoji_1f431);
        sEmojisMap.put(0x1f42d, R.mipmap.emoji_1f42d);
        sEmojisMap.put(0x1f439, R.mipmap.emoji_1f439);
        sEmojisMap.put(0x1f430, R.mipmap.emoji_1f430);
        sEmojisMap.put(0x1f438, R.mipmap.emoji_1f438);
        sEmojisMap.put(0x1f42f, R.mipmap.emoji_1f42f);
        sEmojisMap.put(0x1f428, R.mipmap.emoji_1f428);
        sEmojisMap.put(0x1f43b, R.mipmap.emoji_1f43b);
        sEmojisMap.put(0x1f437, R.mipmap.emoji_1f437);
        sEmojisMap.put(0x1f43d, R.mipmap.emoji_1f43d);
        sEmojisMap.put(0x1f42e, R.mipmap.emoji_1f42e);
        sEmojisMap.put(0x1f417, R.mipmap.emoji_1f417);
        sEmojisMap.put(0x1f435, R.mipmap.emoji_1f435);
        sEmojisMap.put(0x1f412, R.mipmap.emoji_1f412);
        sEmojisMap.put(0x1f434, R.mipmap.emoji_1f434);
        sEmojisMap.put(0x1f411, R.mipmap.emoji_1f411);
        sEmojisMap.put(0x1f418, R.mipmap.emoji_1f418);
        sEmojisMap.put(0x1f43c, R.mipmap.emoji_1f43c);
        sEmojisMap.put(0x1f427, R.mipmap.emoji_1f427);
        sEmojisMap.put(0x1f426, R.mipmap.emoji_1f426);
        sEmojisMap.put(0x1f424, R.mipmap.emoji_1f424);
        sEmojisMap.put(0x1f425, R.mipmap.emoji_1f425);
        sEmojisMap.put(0x1f423, R.mipmap.emoji_1f423);
        sEmojisMap.put(0x1f414, R.mipmap.emoji_1f414);
        sEmojisMap.put(0x1f40d, R.mipmap.emoji_1f40d);
        sEmojisMap.put(0x1f422, R.mipmap.emoji_1f422);
        sEmojisMap.put(0x1f41b, R.mipmap.emoji_1f41b);
        sEmojisMap.put(0x1f41d, R.mipmap.emoji_1f41d);
        sEmojisMap.put(0x1f41c, R.mipmap.emoji_1f41c);
        sEmojisMap.put(0x1f41e, R.mipmap.emoji_1f41e);
        sEmojisMap.put(0x1f40c, R.mipmap.emoji_1f40c);
        sEmojisMap.put(0x1f419, R.mipmap.emoji_1f419);
        sEmojisMap.put(0x1f41a, R.mipmap.emoji_1f41a);
        sEmojisMap.put(0x1f420, R.mipmap.emoji_1f420);
        sEmojisMap.put(0x1f41f, R.mipmap.emoji_1f41f);
        sEmojisMap.put(0x1f42c, R.mipmap.emoji_1f42c);
        sEmojisMap.put(0x1f433, R.mipmap.emoji_1f433);
        sEmojisMap.put(0x1f40b, R.mipmap.emoji_1f40b);
        sEmojisMap.put(0x1f404, R.mipmap.emoji_1f404);
        sEmojisMap.put(0x1f40f, R.mipmap.emoji_1f40f);
        sEmojisMap.put(0x1f400, R.mipmap.emoji_1f400);
        sEmojisMap.put(0x1f403, R.mipmap.emoji_1f403);
        sEmojisMap.put(0x1f405, R.mipmap.emoji_1f405);
        sEmojisMap.put(0x1f407, R.mipmap.emoji_1f407);
        sEmojisMap.put(0x1f409, R.mipmap.emoji_1f409);
        sEmojisMap.put(0x1f40e, R.mipmap.emoji_1f40e);
        sEmojisMap.put(0x1f410, R.mipmap.emoji_1f410);
        sEmojisMap.put(0x1f413, R.mipmap.emoji_1f413);
        sEmojisMap.put(0x1f415, R.mipmap.emoji_1f415);
        sEmojisMap.put(0x1f416, R.mipmap.emoji_1f416);
        sEmojisMap.put(0x1f401, R.mipmap.emoji_1f401);
        sEmojisMap.put(0x1f402, R.mipmap.emoji_1f402);
        sEmojisMap.put(0x1f432, R.mipmap.emoji_1f432);
        sEmojisMap.put(0x1f421, R.mipmap.emoji_1f421);
        sEmojisMap.put(0x1f40a, R.mipmap.emoji_1f40a);
        sEmojisMap.put(0x1f42b, R.mipmap.emoji_1f42b);
        sEmojisMap.put(0x1f42a, R.mipmap.emoji_1f42a);
        sEmojisMap.put(0x1f406, R.mipmap.emoji_1f406);
        sEmojisMap.put(0x1f408, R.mipmap.emoji_1f408);
        sEmojisMap.put(0x1f429, R.mipmap.emoji_1f429);
        sEmojisMap.put(0x1f43e, R.mipmap.emoji_1f43e);
        sEmojisMap.put(0x1f490, R.mipmap.emoji_1f490);
        sEmojisMap.put(0x1f338, R.mipmap.emoji_1f338);
        sEmojisMap.put(0x1f337, R.mipmap.emoji_1f337);
        sEmojisMap.put(0x1f340, R.mipmap.emoji_1f340);
        sEmojisMap.put(0x1f339, R.mipmap.emoji_1f339);
        sEmojisMap.put(0x1f33b, R.mipmap.emoji_1f33b);
        sEmojisMap.put(0x1f33a, R.mipmap.emoji_1f33a);
        sEmojisMap.put(0x1f341, R.mipmap.emoji_1f341);
        sEmojisMap.put(0x1f343, R.mipmap.emoji_1f343);
        sEmojisMap.put(0x1f342, R.mipmap.emoji_1f342);
        sEmojisMap.put(0x1f33f, R.mipmap.emoji_1f33f);
        sEmojisMap.put(0x1f33e, R.mipmap.emoji_1f33e);
        sEmojisMap.put(0x1f344, R.mipmap.emoji_1f344);
        sEmojisMap.put(0x1f335, R.mipmap.emoji_1f335);
        sEmojisMap.put(0x1f334, R.mipmap.emoji_1f334);
        sEmojisMap.put(0x1f332, R.mipmap.emoji_1f332);
        sEmojisMap.put(0x1f333, R.mipmap.emoji_1f333);
        sEmojisMap.put(0x1f330, R.mipmap.emoji_1f330);
        sEmojisMap.put(0x1f331, R.mipmap.emoji_1f331);
        sEmojisMap.put(0x1f33c, R.mipmap.emoji_1f33c);
        sEmojisMap.put(0x1f310, R.mipmap.emoji_1f310);
        sEmojisMap.put(0x1f31e, R.mipmap.emoji_1f31e);
        sEmojisMap.put(0x1f31d, R.mipmap.emoji_1f31d);
        sEmojisMap.put(0x1f31a, R.mipmap.emoji_1f31a);
        sEmojisMap.put(0x1f311, R.mipmap.emoji_1f311);
        sEmojisMap.put(0x1f312, R.mipmap.emoji_1f312);
        sEmojisMap.put(0x1f313, R.mipmap.emoji_1f313);
        sEmojisMap.put(0x1f314, R.mipmap.emoji_1f314);
        sEmojisMap.put(0x1f315, R.mipmap.emoji_1f315);
        sEmojisMap.put(0x1f316, R.mipmap.emoji_1f316);
        sEmojisMap.put(0x1f317, R.mipmap.emoji_1f317);
        sEmojisMap.put(0x1f318, R.mipmap.emoji_1f318);
        sEmojisMap.put(0x1f31c, R.mipmap.emoji_1f31c);
        sEmojisMap.put(0x1f31b, R.mipmap.emoji_1f31b);
        sEmojisMap.put(0x1f319, R.mipmap.emoji_1f319);
        sEmojisMap.put(0x1f30d, R.mipmap.emoji_1f30d);
        sEmojisMap.put(0x1f30e, R.mipmap.emoji_1f30e);
        sEmojisMap.put(0x1f30f, R.mipmap.emoji_1f30f);
        sEmojisMap.put(0x1f30b, R.mipmap.emoji_1f30b);
        sEmojisMap.put(0x1f30c, R.mipmap.emoji_1f30c);
        sEmojisMap.put(0x1f320, R.mipmap.emoji_1f303); // TODO (rockerhieu) review this emoji
        sEmojisMap.put(0x2b50, R.mipmap.emoji_2b50);
        sEmojisMap.put(0x2600, R.mipmap.emoji_2600);
        sEmojisMap.put(0x26c5, R.mipmap.emoji_26c5);
        sEmojisMap.put(0x2601, R.mipmap.emoji_2601);
        sEmojisMap.put(0x26a1, R.mipmap.emoji_26a1);
        sEmojisMap.put(0x2614, R.mipmap.emoji_2614);
        sEmojisMap.put(0x2744, R.mipmap.emoji_2744);
        sEmojisMap.put(0x26c4, R.mipmap.emoji_26c4);
        sEmojisMap.put(0x1f300, R.mipmap.emoji_1f300);
        sEmojisMap.put(0x1f301, R.mipmap.emoji_1f301);
        sEmojisMap.put(0x1f308, R.mipmap.emoji_1f308);
        sEmojisMap.put(0x1f30a, R.mipmap.emoji_1f30a);

        // Objects
        sEmojisMap.put(0x1f38d, R.mipmap.emoji_1f38d);
        sEmojisMap.put(0x1f49d, R.mipmap.emoji_1f49d);
        sEmojisMap.put(0x1f38e, R.mipmap.emoji_1f38e);
        sEmojisMap.put(0x1f392, R.mipmap.emoji_1f392);
        sEmojisMap.put(0x1f393, R.mipmap.emoji_1f393);
        sEmojisMap.put(0x1f38f, R.mipmap.emoji_1f38f);
        sEmojisMap.put(0x1f386, R.mipmap.emoji_1f386);
        sEmojisMap.put(0x1f387, R.mipmap.emoji_1f387);
        sEmojisMap.put(0x1f390, R.mipmap.emoji_1f390);
        sEmojisMap.put(0x1f391, R.mipmap.emoji_1f391);
        sEmojisMap.put(0x1f383, R.mipmap.emoji_1f383);
        sEmojisMap.put(0x1f47b, R.mipmap.emoji_1f47b);
        sEmojisMap.put(0x1f385, R.mipmap.emoji_1f385);
        sEmojisMap.put(0x1f384, R.mipmap.emoji_1f384);
        sEmojisMap.put(0x1f381, R.mipmap.emoji_1f381);
        sEmojisMap.put(0x1f38b, R.mipmap.emoji_1f38b);
        sEmojisMap.put(0x1f389, R.mipmap.emoji_1f389);
        sEmojisMap.put(0x1f38a, R.mipmap.emoji_1f38a);
        sEmojisMap.put(0x1f388, R.mipmap.emoji_1f388);
        sEmojisMap.put(0x1f38c, R.mipmap.emoji_1f38c);
        sEmojisMap.put(0x1f52e, R.mipmap.emoji_1f52e);
        sEmojisMap.put(0x1f3a5, R.mipmap.emoji_1f3a5);
        sEmojisMap.put(0x1f4f7, R.mipmap.emoji_1f4f7);
        sEmojisMap.put(0x1f4f9, R.mipmap.emoji_1f4f9);
        sEmojisMap.put(0x1f4fc, R.mipmap.emoji_1f4fc);
        sEmojisMap.put(0x1f4bf, R.mipmap.emoji_1f4bf);
        sEmojisMap.put(0x1f4c0, R.mipmap.emoji_1f4c0);
        sEmojisMap.put(0x1f4bd, R.mipmap.emoji_1f4bd);
        sEmojisMap.put(0x1f4be, R.mipmap.emoji_1f4be);
        sEmojisMap.put(0x1f4bb, R.mipmap.emoji_1f4bb);
        sEmojisMap.put(0x1f4f1, R.mipmap.emoji_1f4f1);
        sEmojisMap.put(0x260e, R.mipmap.emoji_260e);
        sEmojisMap.put(0x1f4de, R.mipmap.emoji_1f4de);
        sEmojisMap.put(0x1f4df, R.mipmap.emoji_1f4df);
        sEmojisMap.put(0x1f4e0, R.mipmap.emoji_1f4e0);
        sEmojisMap.put(0x1f4e1, R.mipmap.emoji_1f4e1);
        sEmojisMap.put(0x1f4fa, R.mipmap.emoji_1f4fa);
        sEmojisMap.put(0x1f4fb, R.mipmap.emoji_1f4fb);
        sEmojisMap.put(0x1f50a, R.mipmap.emoji_1f50a);
        sEmojisMap.put(0x1f509, R.mipmap.emoji_1f509);
        sEmojisMap.put(0x1f508, R.mipmap.emoji_1f508); // TODO (rockerhieu): review this emoji
        sEmojisMap.put(0x1f507, R.mipmap.emoji_1f507);
        sEmojisMap.put(0x1f514, R.mipmap.emoji_1f514);
        sEmojisMap.put(0x1f515, R.mipmap.emoji_1f515);
        sEmojisMap.put(0x1f4e2, R.mipmap.emoji_1f4e2);
        sEmojisMap.put(0x1f4e3, R.mipmap.emoji_1f4e3);
        sEmojisMap.put(0x23f3, R.mipmap.emoji_23f3);
        sEmojisMap.put(0x231b, R.mipmap.emoji_231b);
        sEmojisMap.put(0x23f0, R.mipmap.emoji_23f0);
        sEmojisMap.put(0x231a, R.mipmap.emoji_231a);
        sEmojisMap.put(0x1f513, R.mipmap.emoji_1f513);
        sEmojisMap.put(0x1f512, R.mipmap.emoji_1f512);
        sEmojisMap.put(0x1f50f, R.mipmap.emoji_1f50f);
        sEmojisMap.put(0x1f510, R.mipmap.emoji_1f510);
        sEmojisMap.put(0x1f511, R.mipmap.emoji_1f511);
        sEmojisMap.put(0x1f50e, R.mipmap.emoji_1f50e);
        sEmojisMap.put(0x1f4a1, R.mipmap.emoji_1f4a1);
        sEmojisMap.put(0x1f526, R.mipmap.emoji_1f526);
        sEmojisMap.put(0x1f506, R.mipmap.emoji_1f506);
        sEmojisMap.put(0x1f505, R.mipmap.emoji_1f505);
        sEmojisMap.put(0x1f50c, R.mipmap.emoji_1f50c);
        sEmojisMap.put(0x1f50b, R.mipmap.emoji_1f50b);
        sEmojisMap.put(0x1f50d, R.mipmap.emoji_1f50d);
        sEmojisMap.put(0x1f6c1, R.mipmap.emoji_1f6c1);
        sEmojisMap.put(0x1f6c0, R.mipmap.emoji_1f6c0);
        sEmojisMap.put(0x1f6bf, R.mipmap.emoji_1f6bf);
        sEmojisMap.put(0x1f6bd, R.mipmap.emoji_1f6bd);
        sEmojisMap.put(0x1f527, R.mipmap.emoji_1f527);
        sEmojisMap.put(0x1f529, R.mipmap.emoji_1f529);
        sEmojisMap.put(0x1f528, R.mipmap.emoji_1f528);
        sEmojisMap.put(0x1f6aa, R.mipmap.emoji_1f6aa);
        sEmojisMap.put(0x1f6ac, R.mipmap.emoji_1f6ac);
        sEmojisMap.put(0x1f4a3, R.mipmap.emoji_1f4a3);
        sEmojisMap.put(0x1f52b, R.mipmap.emoji_1f52b);
        sEmojisMap.put(0x1f52a, R.mipmap.emoji_1f52a);
        sEmojisMap.put(0x1f48a, R.mipmap.emoji_1f48a);
        sEmojisMap.put(0x1f489, R.mipmap.emoji_1f489);
        sEmojisMap.put(0x1f4b0, R.mipmap.emoji_1f4b0);
        sEmojisMap.put(0x1f4b4, R.mipmap.emoji_1f4b4);
        sEmojisMap.put(0x1f4b5, R.mipmap.emoji_1f4b5);
        sEmojisMap.put(0x1f4b7, R.mipmap.emoji_1f4b7);
        sEmojisMap.put(0x1f4b6, R.mipmap.emoji_1f4b6);
        sEmojisMap.put(0x1f4b3, R.mipmap.emoji_1f4b3);
        sEmojisMap.put(0x1f4b8, R.mipmap.emoji_1f4b8);
        sEmojisMap.put(0x1f4f2, R.mipmap.emoji_1f4f2);
        sEmojisMap.put(0x1f4e7, R.mipmap.emoji_1f4e7);
        sEmojisMap.put(0x1f4e5, R.mipmap.emoji_1f4e5);
        sEmojisMap.put(0x1f4e4, R.mipmap.emoji_1f4e4);
        sEmojisMap.put(0x2709, R.mipmap.emoji_2709);
        sEmojisMap.put(0x1f4e9, R.mipmap.emoji_1f4e9);
        sEmojisMap.put(0x1f4e8, R.mipmap.emoji_1f4e8);
        sEmojisMap.put(0x1f4ef, R.mipmap.emoji_1f4ef);
        sEmojisMap.put(0x1f4eb, R.mipmap.emoji_1f4eb);
        sEmojisMap.put(0x1f4ea, R.mipmap.emoji_1f4ea);
        sEmojisMap.put(0x1f4ec, R.mipmap.emoji_1f4ec);
        sEmojisMap.put(0x1f4ed, R.mipmap.emoji_1f4ed);
        sEmojisMap.put(0x1f4ee, R.mipmap.emoji_1f4ee);
        sEmojisMap.put(0x1f4e6, R.mipmap.emoji_1f4e6);
        sEmojisMap.put(0x1f4dd, R.mipmap.emoji_1f4dd);
        sEmojisMap.put(0x1f4c4, R.mipmap.emoji_1f4c4);
        sEmojisMap.put(0x1f4c3, R.mipmap.emoji_1f4c3);
        sEmojisMap.put(0x1f4d1, R.mipmap.emoji_1f4d1);
        sEmojisMap.put(0x1f4ca, R.mipmap.emoji_1f4ca);
        sEmojisMap.put(0x1f4c8, R.mipmap.emoji_1f4c8);
        sEmojisMap.put(0x1f4c9, R.mipmap.emoji_1f4c9);
        sEmojisMap.put(0x1f4dc, R.mipmap.emoji_1f4dc);
        sEmojisMap.put(0x1f4cb, R.mipmap.emoji_1f4cb);
        sEmojisMap.put(0x1f4c5, R.mipmap.emoji_1f4c5);
        sEmojisMap.put(0x1f4c6, R.mipmap.emoji_1f4c6);
        sEmojisMap.put(0x1f4c7, R.mipmap.emoji_1f4c7);
        sEmojisMap.put(0x1f4c1, R.mipmap.emoji_1f4c1);
        sEmojisMap.put(0x1f4c2, R.mipmap.emoji_1f4c2);
        sEmojisMap.put(0x2702, R.mipmap.emoji_2702);
        sEmojisMap.put(0x1f4cc, R.mipmap.emoji_1f4cc);
        sEmojisMap.put(0x1f4ce, R.mipmap.emoji_1f4ce);
        sEmojisMap.put(0x2712, R.mipmap.emoji_2712);
        sEmojisMap.put(0x270f, R.mipmap.emoji_270f);
        sEmojisMap.put(0x1f4cf, R.mipmap.emoji_1f4cf);
        sEmojisMap.put(0x1f4d0, R.mipmap.emoji_1f4d0);
        sEmojisMap.put(0x1f4d5, R.mipmap.emoji_1f4d5);
        sEmojisMap.put(0x1f4d7, R.mipmap.emoji_1f4d7);
        sEmojisMap.put(0x1f4d8, R.mipmap.emoji_1f4d8);
        sEmojisMap.put(0x1f4d9, R.mipmap.emoji_1f4d9);
        sEmojisMap.put(0x1f4d3, R.mipmap.emoji_1f4d3);
        sEmojisMap.put(0x1f4d4, R.mipmap.emoji_1f4d4);
        sEmojisMap.put(0x1f4d2, R.mipmap.emoji_1f4d2);
        sEmojisMap.put(0x1f4da, R.mipmap.emoji_1f4da);
        sEmojisMap.put(0x1f4d6, R.mipmap.emoji_1f4d6);
        sEmojisMap.put(0x1f516, R.mipmap.emoji_1f516);
        sEmojisMap.put(0x1f4db, R.mipmap.emoji_1f4db);
        sEmojisMap.put(0x1f52c, R.mipmap.emoji_1f52c);
        sEmojisMap.put(0x1f52d, R.mipmap.emoji_1f52d);
        sEmojisMap.put(0x1f4f0, R.mipmap.emoji_1f4f0);
        sEmojisMap.put(0x1f3a8, R.mipmap.emoji_1f3a8);
        sEmojisMap.put(0x1f3ac, R.mipmap.emoji_1f3ac);
        sEmojisMap.put(0x1f3a4, R.mipmap.emoji_1f3a4);
        sEmojisMap.put(0x1f3a7, R.mipmap.emoji_1f3a7);
        sEmojisMap.put(0x1f3bc, R.mipmap.emoji_1f3bc);
        sEmojisMap.put(0x1f3b5, R.mipmap.emoji_1f3b5);
        sEmojisMap.put(0x1f3b6, R.mipmap.emoji_1f3b6);
        sEmojisMap.put(0x1f3b9, R.mipmap.emoji_1f3b9);
        sEmojisMap.put(0x1f3bb, R.mipmap.emoji_1f3bb);
        sEmojisMap.put(0x1f3ba, R.mipmap.emoji_1f3ba);
        sEmojisMap.put(0x1f3b7, R.mipmap.emoji_1f3b7);
        sEmojisMap.put(0x1f3b8, R.mipmap.emoji_1f3b8);
        sEmojisMap.put(0x1f47e, R.mipmap.emoji_1f47e);
        sEmojisMap.put(0x1f3ae, R.mipmap.emoji_1f3ae);
        sEmojisMap.put(0x1f0cf, R.mipmap.emoji_1f0cf);
        sEmojisMap.put(0x1f3b4, R.mipmap.emoji_1f3b4);
        sEmojisMap.put(0x1f004, R.mipmap.emoji_1f004);
        sEmojisMap.put(0x1f3b2, R.mipmap.emoji_1f3b2);
        sEmojisMap.put(0x1f3af, R.mipmap.emoji_1f3af);
        sEmojisMap.put(0x1f3c8, R.mipmap.emoji_1f3c8);
        sEmojisMap.put(0x1f3c0, R.mipmap.emoji_1f3c0);
        sEmojisMap.put(0x26bd, R.mipmap.emoji_26bd);
        sEmojisMap.put(0x26be, R.mipmap.emoji_26be);
        sEmojisMap.put(0x1f3be, R.mipmap.emoji_1f3be);
        sEmojisMap.put(0x1f3b1, R.mipmap.emoji_1f3b1);
        sEmojisMap.put(0x1f3c9, R.mipmap.emoji_1f3c9);
        sEmojisMap.put(0x1f3b3, R.mipmap.emoji_1f3b3);
        sEmojisMap.put(0x26f3, R.mipmap.emoji_26f3);
        sEmojisMap.put(0x1f6b5, R.mipmap.emoji_1f6b5);
        sEmojisMap.put(0x1f6b4, R.mipmap.emoji_1f6b4);
        sEmojisMap.put(0x1f3c1, R.mipmap.emoji_1f3c1);
        sEmojisMap.put(0x1f3c7, R.mipmap.emoji_1f3c7);
        sEmojisMap.put(0x1f3c6, R.mipmap.emoji_1f3c6);
        sEmojisMap.put(0x1f3bf, R.mipmap.emoji_1f3bf);
        sEmojisMap.put(0x1f3c2, R.mipmap.emoji_1f3c2);
        sEmojisMap.put(0x1f3ca, R.mipmap.emoji_1f3ca);
        sEmojisMap.put(0x1f3c4, R.mipmap.emoji_1f3c4);
        sEmojisMap.put(0x1f3a3, R.mipmap.emoji_1f3a3);
        sEmojisMap.put(0x2615, R.mipmap.emoji_2615);
        sEmojisMap.put(0x1f375, R.mipmap.emoji_1f375);
        sEmojisMap.put(0x1f376, R.mipmap.emoji_1f376);
        sEmojisMap.put(0x1f37c, R.mipmap.emoji_1f37c);
        sEmojisMap.put(0x1f37a, R.mipmap.emoji_1f37a);
        sEmojisMap.put(0x1f37b, R.mipmap.emoji_1f37b);
        sEmojisMap.put(0x1f378, R.mipmap.emoji_1f378);
        sEmojisMap.put(0x1f379, R.mipmap.emoji_1f379);
        sEmojisMap.put(0x1f377, R.mipmap.emoji_1f377);
        sEmojisMap.put(0x1f374, R.mipmap.emoji_1f374);
        sEmojisMap.put(0x1f355, R.mipmap.emoji_1f355);
        sEmojisMap.put(0x1f354, R.mipmap.emoji_1f354);
        sEmojisMap.put(0x1f35f, R.mipmap.emoji_1f35f);
        sEmojisMap.put(0x1f357, R.mipmap.emoji_1f357);
        sEmojisMap.put(0x1f356, R.mipmap.emoji_1f356);
        sEmojisMap.put(0x1f35d, R.mipmap.emoji_1f35d);
        sEmojisMap.put(0x1f35b, R.mipmap.emoji_1f35b);
        sEmojisMap.put(0x1f364, R.mipmap.emoji_1f364);
        sEmojisMap.put(0x1f371, R.mipmap.emoji_1f371);
        sEmojisMap.put(0x1f363, R.mipmap.emoji_1f363);
        sEmojisMap.put(0x1f365, R.mipmap.emoji_1f365);
        sEmojisMap.put(0x1f359, R.mipmap.emoji_1f359);
        sEmojisMap.put(0x1f358, R.mipmap.emoji_1f358);
        sEmojisMap.put(0x1f35a, R.mipmap.emoji_1f35a);
        sEmojisMap.put(0x1f35c, R.mipmap.emoji_1f35c);
        sEmojisMap.put(0x1f372, R.mipmap.emoji_1f372);
        sEmojisMap.put(0x1f362, R.mipmap.emoji_1f362);
        sEmojisMap.put(0x1f361, R.mipmap.emoji_1f361);
        sEmojisMap.put(0x1f373, R.mipmap.emoji_1f373);
        sEmojisMap.put(0x1f35e, R.mipmap.emoji_1f35e);
        sEmojisMap.put(0x1f369, R.mipmap.emoji_1f369);
        sEmojisMap.put(0x1f36e, R.mipmap.emoji_1f36e);
        sEmojisMap.put(0x1f366, R.mipmap.emoji_1f366);
        sEmojisMap.put(0x1f368, R.mipmap.emoji_1f368);
        sEmojisMap.put(0x1f367, R.mipmap.emoji_1f367);
        sEmojisMap.put(0x1f382, R.mipmap.emoji_1f382);
        sEmojisMap.put(0x1f370, R.mipmap.emoji_1f370);
        sEmojisMap.put(0x1f36a, R.mipmap.emoji_1f36a);
        sEmojisMap.put(0x1f36b, R.mipmap.emoji_1f36b);
        sEmojisMap.put(0x1f36c, R.mipmap.emoji_1f36c);
        sEmojisMap.put(0x1f36d, R.mipmap.emoji_1f36d);
        sEmojisMap.put(0x1f36f, R.mipmap.emoji_1f36f);
        sEmojisMap.put(0x1f34e, R.mipmap.emoji_1f34e);
        sEmojisMap.put(0x1f34f, R.mipmap.emoji_1f34f);
        sEmojisMap.put(0x1f34a, R.mipmap.emoji_1f34a);
        sEmojisMap.put(0x1f34b, R.mipmap.emoji_1f34b);
        sEmojisMap.put(0x1f352, R.mipmap.emoji_1f352);
        sEmojisMap.put(0x1f347, R.mipmap.emoji_1f347);
        sEmojisMap.put(0x1f349, R.mipmap.emoji_1f349);
        sEmojisMap.put(0x1f353, R.mipmap.emoji_1f353);
        sEmojisMap.put(0x1f351, R.mipmap.emoji_1f351);
        sEmojisMap.put(0x1f348, R.mipmap.emoji_1f348);
        sEmojisMap.put(0x1f34c, R.mipmap.emoji_1f34c);
        sEmojisMap.put(0x1f350, R.mipmap.emoji_1f350);
        sEmojisMap.put(0x1f34d, R.mipmap.emoji_1f34d);
        sEmojisMap.put(0x1f360, R.mipmap.emoji_1f360);
        sEmojisMap.put(0x1f346, R.mipmap.emoji_1f346);
        sEmojisMap.put(0x1f345, R.mipmap.emoji_1f345);
        sEmojisMap.put(0x1f33d, R.mipmap.emoji_1f33d);

        // Places
        sEmojisMap.put(0x1f3e0, R.mipmap.emoji_1f3e0);
        sEmojisMap.put(0x1f3e1, R.mipmap.emoji_1f3e1);
        sEmojisMap.put(0x1f3eb, R.mipmap.emoji_1f3eb);
        sEmojisMap.put(0x1f3e2, R.mipmap.emoji_1f3e2);
        sEmojisMap.put(0x1f3e3, R.mipmap.emoji_1f3e3);
        sEmojisMap.put(0x1f3e5, R.mipmap.emoji_1f3e5);
        sEmojisMap.put(0x1f3e6, R.mipmap.emoji_1f3e6);
        sEmojisMap.put(0x1f3ea, R.mipmap.emoji_1f3ea);
        sEmojisMap.put(0x1f3e9, R.mipmap.emoji_1f3e9);
        sEmojisMap.put(0x1f3e8, R.mipmap.emoji_1f3e8);
        sEmojisMap.put(0x1f492, R.mipmap.emoji_1f492);
        sEmojisMap.put(0x26ea, R.mipmap.emoji_26ea);
        sEmojisMap.put(0x1f3ec, R.mipmap.emoji_1f3ec);
        sEmojisMap.put(0x1f3e4, R.mipmap.emoji_1f3e4);
        sEmojisMap.put(0x1f307, R.mipmap.emoji_1f307);
        sEmojisMap.put(0x1f306, R.mipmap.emoji_1f306);
        sEmojisMap.put(0x1f3ef, R.mipmap.emoji_1f3ef);
        sEmojisMap.put(0x1f3f0, R.mipmap.emoji_1f3f0);
        sEmojisMap.put(0x26fa, R.mipmap.emoji_26fa);
        sEmojisMap.put(0x1f3ed, R.mipmap.emoji_1f3ed);
        sEmojisMap.put(0x1f5fc, R.mipmap.emoji_1f5fc);
        sEmojisMap.put(0x1f5fe, R.mipmap.emoji_1f5fe);
        sEmojisMap.put(0x1f5fb, R.mipmap.emoji_1f5fb);
        sEmojisMap.put(0x1f304, R.mipmap.emoji_1f304);
        sEmojisMap.put(0x1f305, R.mipmap.emoji_1f305);
        sEmojisMap.put(0x1f303, R.mipmap.emoji_1f303);
        sEmojisMap.put(0x1f5fd, R.mipmap.emoji_1f5fd);
        sEmojisMap.put(0x1f309, R.mipmap.emoji_1f309);
        sEmojisMap.put(0x1f3a0, R.mipmap.emoji_1f3a0);
        sEmojisMap.put(0x1f3a1, R.mipmap.emoji_1f3a1);
        sEmojisMap.put(0x26f2, R.mipmap.emoji_26f2);
        sEmojisMap.put(0x1f3a2, R.mipmap.emoji_1f3a2);
        sEmojisMap.put(0x1f6a2, R.mipmap.emoji_1f6a2);
        sEmojisMap.put(0x26f5, R.mipmap.emoji_26f5);
        sEmojisMap.put(0x1f6a4, R.mipmap.emoji_1f6a4);
        sEmojisMap.put(0x1f6a3, R.mipmap.emoji_1f6a3);
        sEmojisMap.put(0x2693, R.mipmap.emoji_2693);
        sEmojisMap.put(0x1f680, R.mipmap.emoji_1f680);
        sEmojisMap.put(0x2708, R.mipmap.emoji_2708);
        sEmojisMap.put(0x1f4ba, R.mipmap.emoji_1f4ba);
        sEmojisMap.put(0x1f681, R.mipmap.emoji_1f681);
        sEmojisMap.put(0x1f682, R.mipmap.emoji_1f682);
        sEmojisMap.put(0x1f68a, R.mipmap.emoji_1f68a);
        sEmojisMap.put(0x1f689, R.mipmap.emoji_1f689);
        sEmojisMap.put(0x1f69e, R.mipmap.emoji_1f69e);
        sEmojisMap.put(0x1f686, R.mipmap.emoji_1f686);
        sEmojisMap.put(0x1f684, R.mipmap.emoji_1f684);
        sEmojisMap.put(0x1f685, R.mipmap.emoji_1f685);
        sEmojisMap.put(0x1f688, R.mipmap.emoji_1f688);
        sEmojisMap.put(0x1f687, R.mipmap.emoji_1f687);
        sEmojisMap.put(0x1f69d, R.mipmap.emoji_1f69d);
        sEmojisMap.put(0x1f68b, R.mipmap.emoji_1f68b); // TODO (rockerhieu) review this emoji
        sEmojisMap.put(0x1f683, R.mipmap.emoji_1f683);
        sEmojisMap.put(0x1f68e, R.mipmap.emoji_1f68e);
        sEmojisMap.put(0x1f68c, R.mipmap.emoji_1f68c);
        sEmojisMap.put(0x1f68d, R.mipmap.emoji_1f68d);
        sEmojisMap.put(0x1f699, R.mipmap.emoji_1f699);
        sEmojisMap.put(0x1f698, R.mipmap.emoji_1f698);
        sEmojisMap.put(0x1f697, R.mipmap.emoji_1f697);
        sEmojisMap.put(0x1f695, R.mipmap.emoji_1f695);
        sEmojisMap.put(0x1f696, R.mipmap.emoji_1f696);
        sEmojisMap.put(0x1f69b, R.mipmap.emoji_1f69b);
        sEmojisMap.put(0x1f69a, R.mipmap.emoji_1f69a);
        sEmojisMap.put(0x1f6a8, R.mipmap.emoji_1f6a8);
        sEmojisMap.put(0x1f693, R.mipmap.emoji_1f693);
        sEmojisMap.put(0x1f694, R.mipmap.emoji_1f694);
        sEmojisMap.put(0x1f692, R.mipmap.emoji_1f692);
        sEmojisMap.put(0x1f691, R.mipmap.emoji_1f691);
        sEmojisMap.put(0x1f690, R.mipmap.emoji_1f690);
        sEmojisMap.put(0x1f6b2, R.mipmap.emoji_1f6b2);
        sEmojisMap.put(0x1f6a1, R.mipmap.emoji_1f6a1);
        sEmojisMap.put(0x1f69f, R.mipmap.emoji_1f69f);
        sEmojisMap.put(0x1f6a0, R.mipmap.emoji_1f6a0);
        sEmojisMap.put(0x1f69c, R.mipmap.emoji_1f69c);
        sEmojisMap.put(0x1f488, R.mipmap.emoji_1f488);
        sEmojisMap.put(0x1f68f, R.mipmap.emoji_1f68f);
        sEmojisMap.put(0x1f3ab, R.mipmap.emoji_1f3ab);
        sEmojisMap.put(0x1f6a6, R.mipmap.emoji_1f6a6);
        sEmojisMap.put(0x1f6a5, R.mipmap.emoji_1f6a5);
        sEmojisMap.put(0x26a0, R.mipmap.emoji_26a0);
        sEmojisMap.put(0x1f6a7, R.mipmap.emoji_1f6a7);
        sEmojisMap.put(0x1f530, R.mipmap.emoji_1f530);
        sEmojisMap.put(0x26fd, R.mipmap.emoji_26fd);
        sEmojisMap.put(0x1f3ee, R.mipmap.emoji_1f3ee);
        sEmojisMap.put(0x1f3b0, R.mipmap.emoji_1f3b0);
        sEmojisMap.put(0x2668, R.mipmap.emoji_2668);
        sEmojisMap.put(0x1f5ff, R.mipmap.emoji_1f5ff);
        sEmojisMap.put(0x1f3aa, R.mipmap.emoji_1f3aa);
        sEmojisMap.put(0x1f3ad, R.mipmap.emoji_1f3ad);
        sEmojisMap.put(0x1f4cd, R.mipmap.emoji_1f4cd);
        sEmojisMap.put(0x1f6a9, R.mipmap.emoji_1f6a9);
//        Emoji.fromChars("\ud83c\uddef\ud83c\uddf5");
//        Emoji.fromChars("\ud83c\uddf0\ud83c\uddf7");
//        Emoji.fromChars("\ud83c\udde9\ud83c\uddea");
//        Emoji.fromChars("\ud83c\udde8\ud83c\uddf3");
//        Emoji.fromChars("\ud83c\uddfa\ud83c\uddf8");
//        Emoji.fromChars("\ud83c\uddeb\ud83c\uddf7");
//        Emoji.fromChars("\ud83c\uddea\ud83c\uddf8");
//        Emoji.fromChars("\ud83c\uddee\ud83c\uddf9");
//        Emoji.fromChars("\ud83c\uddf7\ud83c\uddfa");
//        Emoji.fromChars("\ud83c\uddec\ud83c\udde7");

        // Symbols
//        Emoji.fromChars("\u0031\u20e3"),
//        Emoji.fromChars("\u0032\u20e3"),
//        Emoji.fromChars("\u0033\u20e3"),
//        Emoji.fromChars("\u0034\u20e3"),
//        Emoji.fromChars("\u0035\u20e3"),
//        Emoji.fromChars("\u0036\u20e3"),
//        Emoji.fromChars("\u0037\u20e3"),
//        Emoji.fromChars("\u0038\u20e3"),
//        Emoji.fromChars("\u0039\u20e3"),
//        Emoji.fromChars("\u0030\u20e3"),
        sEmojisMap.put(0x1f51f, R.mipmap.emoji_1f51f);
        sEmojisMap.put(0x1f522, R.mipmap.emoji_1f522);
//        Emoji.fromChars("\u0023\u20e3"),
        sEmojisMap.put(0x1f523, R.mipmap.emoji_1f523);
        sEmojisMap.put(0x2b06, R.mipmap.emoji_2b06);
        sEmojisMap.put(0x2b07, R.mipmap.emoji_2b07);
        sEmojisMap.put(0x2b05, R.mipmap.emoji_2b05);
        sEmojisMap.put(0x27a1, R.mipmap.emoji_27a1);
        sEmojisMap.put(0x1f520, R.mipmap.emoji_1f520);
        sEmojisMap.put(0x1f521, R.mipmap.emoji_1f521);
        sEmojisMap.put(0x1f524, R.mipmap.emoji_1f524);
        sEmojisMap.put(0x2197, R.mipmap.emoji_2197);
        sEmojisMap.put(0x2196, R.mipmap.emoji_2196);
        sEmojisMap.put(0x2198, R.mipmap.emoji_2198);
        sEmojisMap.put(0x2199, R.mipmap.emoji_2199);
        sEmojisMap.put(0x2194, R.mipmap.emoji_2194);
        sEmojisMap.put(0x2195, R.mipmap.emoji_2195);
        sEmojisMap.put(0x1f504, R.mipmap.emoji_1f504);
        sEmojisMap.put(0x25c0, R.mipmap.emoji_25c0);
        sEmojisMap.put(0x25b6, R.mipmap.emoji_25b6);
        sEmojisMap.put(0x1f53c, R.mipmap.emoji_1f53c);
        sEmojisMap.put(0x1f53d, R.mipmap.emoji_1f53d);
        sEmojisMap.put(0x21a9, R.mipmap.emoji_21a9);
        sEmojisMap.put(0x21aa, R.mipmap.emoji_21aa);
        sEmojisMap.put(0x2139, R.mipmap.emoji_2139);
        sEmojisMap.put(0x23ea, R.mipmap.emoji_23ea);
        sEmojisMap.put(0x23e9, R.mipmap.emoji_23e9);
        sEmojisMap.put(0x23eb, R.mipmap.emoji_23eb);
        sEmojisMap.put(0x23ec, R.mipmap.emoji_23ec);
        sEmojisMap.put(0x2935, R.mipmap.emoji_2935);
        sEmojisMap.put(0x2934, R.mipmap.emoji_2934);
        sEmojisMap.put(0x1f197, R.mipmap.emoji_1f197);
        sEmojisMap.put(0x1f500, R.mipmap.emoji_1f500);
        sEmojisMap.put(0x1f501, R.mipmap.emoji_1f501);
        sEmojisMap.put(0x1f502, R.mipmap.emoji_1f502);
        sEmojisMap.put(0x1f195, R.mipmap.emoji_1f195);
        sEmojisMap.put(0x1f199, R.mipmap.emoji_1f199);
        sEmojisMap.put(0x1f192, R.mipmap.emoji_1f192);
        sEmojisMap.put(0x1f193, R.mipmap.emoji_1f193);
        sEmojisMap.put(0x1f196, R.mipmap.emoji_1f196);
        sEmojisMap.put(0x1f4f6, R.mipmap.emoji_1f4f6);
        sEmojisMap.put(0x1f3a6, R.mipmap.emoji_1f3a6);
        sEmojisMap.put(0x1f201, R.mipmap.emoji_1f201);
        sEmojisMap.put(0x1f22f, R.mipmap.emoji_1f22f);
        sEmojisMap.put(0x1f233, R.mipmap.emoji_1f233);
        sEmojisMap.put(0x1f235, R.mipmap.emoji_1f235);
        sEmojisMap.put(0x1f234, R.mipmap.emoji_1f234);
        sEmojisMap.put(0x1f232, R.mipmap.emoji_1f232);
        sEmojisMap.put(0x1f250, R.mipmap.emoji_1f250);
        sEmojisMap.put(0x1f239, R.mipmap.emoji_1f239);
        sEmojisMap.put(0x1f23a, R.mipmap.emoji_1f23a);
        sEmojisMap.put(0x1f236, R.mipmap.emoji_1f236);
        sEmojisMap.put(0x1f21a, R.mipmap.emoji_1f21a);
        sEmojisMap.put(0x1f6bb, R.mipmap.emoji_1f6bb);
        sEmojisMap.put(0x1f6b9, R.mipmap.emoji_1f6b9);
        sEmojisMap.put(0x1f6ba, R.mipmap.emoji_1f6ba);
        sEmojisMap.put(0x1f6bc, R.mipmap.emoji_1f6bc);
        sEmojisMap.put(0x1f6be, R.mipmap.emoji_1f6be);
        sEmojisMap.put(0x1f6b0, R.mipmap.emoji_1f6b0);
        sEmojisMap.put(0x1f6ae, R.mipmap.emoji_1f6ae);
        sEmojisMap.put(0x1f17f, R.mipmap.emoji_1f17f);
        sEmojisMap.put(0x267f, R.mipmap.emoji_267f);
        sEmojisMap.put(0x1f6ad, R.mipmap.emoji_1f6ad);
        sEmojisMap.put(0x1f237, R.mipmap.emoji_1f237);
        sEmojisMap.put(0x1f238, R.mipmap.emoji_1f238);
        sEmojisMap.put(0x1f202, R.mipmap.emoji_1f202);
        sEmojisMap.put(0x24c2, R.mipmap.emoji_24c2);
        sEmojisMap.put(0x1f6c2, R.mipmap.emoji_1f6c2);
        sEmojisMap.put(0x1f6c4, R.mipmap.emoji_1f6c4);
        sEmojisMap.put(0x1f6c5, R.mipmap.emoji_1f6c5);
        sEmojisMap.put(0x1f6c3, R.mipmap.emoji_1f6c3);
        sEmojisMap.put(0x1f251, R.mipmap.emoji_1f251);
        sEmojisMap.put(0x3299, R.mipmap.emoji_3299);
        sEmojisMap.put(0x3297, R.mipmap.emoji_3297);
        sEmojisMap.put(0x1f191, R.mipmap.emoji_1f191);
        sEmojisMap.put(0x1f198, R.mipmap.emoji_1f198);
        sEmojisMap.put(0x1f194, R.mipmap.emoji_1f194);
        sEmojisMap.put(0x1f6ab, R.mipmap.emoji_1f6ab);
        sEmojisMap.put(0x1f51e, R.mipmap.emoji_1f51e);
        sEmojisMap.put(0x1f4f5, R.mipmap.emoji_1f4f5);
        sEmojisMap.put(0x1f6af, R.mipmap.emoji_1f6af);
        sEmojisMap.put(0x1f6b1, R.mipmap.emoji_1f6b1);
        sEmojisMap.put(0x1f6b3, R.mipmap.emoji_1f6b3);
        sEmojisMap.put(0x1f6b7, R.mipmap.emoji_1f6b7);
        sEmojisMap.put(0x1f6b8, R.mipmap.emoji_1f6b8);
        sEmojisMap.put(0x26d4, R.mipmap.emoji_26d4);
        sEmojisMap.put(0x2733, R.mipmap.emoji_2733);
        sEmojisMap.put(0x2747, R.mipmap.emoji_2747);
        sEmojisMap.put(0x274e, R.mipmap.emoji_274e);
        sEmojisMap.put(0x2705, R.mipmap.emoji_2705);
        sEmojisMap.put(0x2734, R.mipmap.emoji_2734);
        sEmojisMap.put(0x1f49f, R.mipmap.emoji_1f49f);
        sEmojisMap.put(0x1f19a, R.mipmap.emoji_1f19a);
        sEmojisMap.put(0x1f4f3, R.mipmap.emoji_1f4f3);
        sEmojisMap.put(0x1f4f4, R.mipmap.emoji_1f4f4);
        sEmojisMap.put(0x1f170, R.mipmap.emoji_1f170);
        sEmojisMap.put(0x1f171, R.mipmap.emoji_1f171);
        sEmojisMap.put(0x1f18e, R.mipmap.emoji_1f18e);
        sEmojisMap.put(0x1f17e, R.mipmap.emoji_1f17e);
        sEmojisMap.put(0x1f4a0, R.mipmap.emoji_1f4a0);
        sEmojisMap.put(0x27bf, R.mipmap.emoji_27bf);
        sEmojisMap.put(0x267b, R.mipmap.emoji_267b);
        sEmojisMap.put(0x2648, R.mipmap.emoji_2648);
        sEmojisMap.put(0x2649, R.mipmap.emoji_2649);
        sEmojisMap.put(0x264a, R.mipmap.emoji_264a);
        sEmojisMap.put(0x264b, R.mipmap.emoji_264b);
        sEmojisMap.put(0x264c, R.mipmap.emoji_264c);
        sEmojisMap.put(0x264d, R.mipmap.emoji_264d);
        sEmojisMap.put(0x264e, R.mipmap.emoji_264e);
        sEmojisMap.put(0x264f, R.mipmap.emoji_264f);
        sEmojisMap.put(0x2650, R.mipmap.emoji_2650);
        sEmojisMap.put(0x2651, R.mipmap.emoji_2651);
        sEmojisMap.put(0x2652, R.mipmap.emoji_2652);
        sEmojisMap.put(0x2653, R.mipmap.emoji_2653);
        sEmojisMap.put(0x26ce, R.mipmap.emoji_26ce);
        sEmojisMap.put(0x1f52f, R.mipmap.emoji_1f52f);
        sEmojisMap.put(0x1f3e7, R.mipmap.emoji_1f3e7);
        sEmojisMap.put(0x1f4b9, R.mipmap.emoji_1f4b9);
        sEmojisMap.put(0x1f4b2, R.mipmap.emoji_1f4b2);
        sEmojisMap.put(0x1f4b1, R.mipmap.emoji_1f4b1);
        sEmojisMap.put(0x00a9, R.mipmap.emoji_00a9);
        sEmojisMap.put(0x00ae, R.mipmap.emoji_00ae);
        sEmojisMap.put(0x2122, R.mipmap.emoji_2122);
        sEmojisMap.put(0x274c, R.mipmap.emoji_274c);
        sEmojisMap.put(0x203c, R.mipmap.emoji_203c);
        sEmojisMap.put(0x2049, R.mipmap.emoji_2049);
        sEmojisMap.put(0x2757, R.mipmap.emoji_2757);
        sEmojisMap.put(0x2753, R.mipmap.emoji_2753);
        sEmojisMap.put(0x2755, R.mipmap.emoji_2755);
        sEmojisMap.put(0x2754, R.mipmap.emoji_2754);
        sEmojisMap.put(0x2b55, R.mipmap.emoji_2b55);
        sEmojisMap.put(0x1f51d, R.mipmap.emoji_1f51d);
        sEmojisMap.put(0x1f51a, R.mipmap.emoji_1f51a);
        sEmojisMap.put(0x1f519, R.mipmap.emoji_1f519);
        sEmojisMap.put(0x1f51b, R.mipmap.emoji_1f51b);
        sEmojisMap.put(0x1f51c, R.mipmap.emoji_1f51c);
        sEmojisMap.put(0x1f503, R.mipmap.emoji_1f503);
        sEmojisMap.put(0x1f55b, R.mipmap.emoji_1f55b);
        sEmojisMap.put(0x1f567, R.mipmap.emoji_1f567);
        sEmojisMap.put(0x1f550, R.mipmap.emoji_1f550);
        sEmojisMap.put(0x1f55c, R.mipmap.emoji_1f55c);
        sEmojisMap.put(0x1f551, R.mipmap.emoji_1f551);
        sEmojisMap.put(0x1f55d, R.mipmap.emoji_1f55d);
        sEmojisMap.put(0x1f552, R.mipmap.emoji_1f552);
        sEmojisMap.put(0x1f55e, R.mipmap.emoji_1f55e);
        sEmojisMap.put(0x1f553, R.mipmap.emoji_1f553);
        sEmojisMap.put(0x1f55f, R.mipmap.emoji_1f55f);
        sEmojisMap.put(0x1f554, R.mipmap.emoji_1f554);
        sEmojisMap.put(0x1f560, R.mipmap.emoji_1f560);
        sEmojisMap.put(0x1f555, R.mipmap.emoji_1f555);
        sEmojisMap.put(0x1f556, R.mipmap.emoji_1f556);
        sEmojisMap.put(0x1f557, R.mipmap.emoji_1f557);
        sEmojisMap.put(0x1f558, R.mipmap.emoji_1f558);
        sEmojisMap.put(0x1f559, R.mipmap.emoji_1f559);
        sEmojisMap.put(0x1f55a, R.mipmap.emoji_1f55a);
        sEmojisMap.put(0x1f561, R.mipmap.emoji_1f561);
        sEmojisMap.put(0x1f562, R.mipmap.emoji_1f562);
        sEmojisMap.put(0x1f563, R.mipmap.emoji_1f563);
        sEmojisMap.put(0x1f564, R.mipmap.emoji_1f564);
        sEmojisMap.put(0x1f565, R.mipmap.emoji_1f565);
        sEmojisMap.put(0x1f566, R.mipmap.emoji_1f566);
        sEmojisMap.put(0x2716, R.mipmap.emoji_2716);
        sEmojisMap.put(0x2795, R.mipmap.emoji_2795);
        sEmojisMap.put(0x2796, R.mipmap.emoji_2796);
        sEmojisMap.put(0x2797, R.mipmap.emoji_2797);
        sEmojisMap.put(0x2660, R.mipmap.emoji_2660);
        sEmojisMap.put(0x2665, R.mipmap.emoji_2665);
        sEmojisMap.put(0x2663, R.mipmap.emoji_2663);
        sEmojisMap.put(0x2666, R.mipmap.emoji_2666);
        sEmojisMap.put(0x1f4ae, R.mipmap.emoji_1f4ae);
        sEmojisMap.put(0x1f4af, R.mipmap.emoji_1f4af);
        sEmojisMap.put(0x2714, R.mipmap.emoji_2714);
        sEmojisMap.put(0x2611, R.mipmap.emoji_2611);
        sEmojisMap.put(0x1f518, R.mipmap.emoji_1f518);
        sEmojisMap.put(0x1f517, R.mipmap.emoji_1f517);
        sEmojisMap.put(0x27b0, R.mipmap.emoji_27b0);
        sEmojisMap.put(0x3030, R.mipmap.emoji_3030);
        sEmojisMap.put(0x303d, R.mipmap.emoji_303d);
        sEmojisMap.put(0x1f531, R.mipmap.emoji_1f531);
        sEmojisMap.put(0x25fc, R.mipmap.emoji_25fc);
        sEmojisMap.put(0x25fb, R.mipmap.emoji_25fb);
        sEmojisMap.put(0x25fe, R.mipmap.emoji_25fe);
        sEmojisMap.put(0x25fd, R.mipmap.emoji_25fd);
        sEmojisMap.put(0x25aa, R.mipmap.emoji_25aa);
        sEmojisMap.put(0x25ab, R.mipmap.emoji_25ab);
        sEmojisMap.put(0x1f53a, R.mipmap.emoji_1f53a);
        sEmojisMap.put(0x1f532, R.mipmap.emoji_1f532);
        sEmojisMap.put(0x1f533, R.mipmap.emoji_1f533);
        sEmojisMap.put(0x26ab, R.mipmap.emoji_26ab);
        sEmojisMap.put(0x26aa, R.mipmap.emoji_26aa);
        sEmojisMap.put(0x1f534, R.mipmap.emoji_1f534);
        sEmojisMap.put(0x1f535, R.mipmap.emoji_1f535);
        sEmojisMap.put(0x1f53b, R.mipmap.emoji_1f53b);
        sEmojisMap.put(0x2b1c, R.mipmap.emoji_2b1c);
        sEmojisMap.put(0x2b1b, R.mipmap.emoji_2b1b);
        sEmojisMap.put(0x1f536, R.mipmap.emoji_1f536);
        sEmojisMap.put(0x1f537, R.mipmap.emoji_1f537);
        sEmojisMap.put(0x1f538, R.mipmap.emoji_1f538);
        sEmojisMap.put(0x1f539, R.mipmap.emoji_1f539);


        sSoftbanksMap.put(0xe001, R.mipmap.emoji_1f466);
        sSoftbanksMap.put(0xe002, R.mipmap.emoji_1f467);
        sSoftbanksMap.put(0xe003, R.mipmap.emoji_1f48b);
        sSoftbanksMap.put(0xe004, R.mipmap.emoji_1f468);
        sSoftbanksMap.put(0xe005, R.mipmap.emoji_1f469);
        sSoftbanksMap.put(0xe006, R.mipmap.emoji_1f455);
        sSoftbanksMap.put(0xe007, R.mipmap.emoji_1f45e);
        sSoftbanksMap.put(0xe008, R.mipmap.emoji_1f4f7);
        sSoftbanksMap.put(0xe009, R.mipmap.emoji_1f4de);
        sSoftbanksMap.put(0xe00a, R.mipmap.emoji_1f4f1);
        sSoftbanksMap.put(0xe00b, R.mipmap.emoji_1f4e0);
        sSoftbanksMap.put(0xe00c, R.mipmap.emoji_1f4bb);
        sSoftbanksMap.put(0xe00d, R.mipmap.emoji_1f44a);
        sSoftbanksMap.put(0xe00e, R.mipmap.emoji_1f44d);
        sSoftbanksMap.put(0xe00f, R.mipmap.emoji_261d);
        sSoftbanksMap.put(0xe010, R.mipmap.emoji_270a);
        sSoftbanksMap.put(0xe011, R.mipmap.emoji_270c);
        sSoftbanksMap.put(0xe012, R.mipmap.emoji_1f64b);
        sSoftbanksMap.put(0xe013, R.mipmap.emoji_1f3bf);
        sSoftbanksMap.put(0xe014, R.mipmap.emoji_26f3);
        sSoftbanksMap.put(0xe015, R.mipmap.emoji_1f3be);
        sSoftbanksMap.put(0xe016, R.mipmap.emoji_26be);
        sSoftbanksMap.put(0xe017, R.mipmap.emoji_1f3c4);
        sSoftbanksMap.put(0xe018, R.mipmap.emoji_26bd);
        sSoftbanksMap.put(0xe019, R.mipmap.emoji_1f3a3);
        sSoftbanksMap.put(0xe01a, R.mipmap.emoji_1f434);
        sSoftbanksMap.put(0xe01b, R.mipmap.emoji_1f697);
        sSoftbanksMap.put(0xe01c, R.mipmap.emoji_26f5);
        sSoftbanksMap.put(0xe01d, R.mipmap.emoji_2708);
        sSoftbanksMap.put(0xe01e, R.mipmap.emoji_1f683);
        sSoftbanksMap.put(0xe01f, R.mipmap.emoji_1f685);
        sSoftbanksMap.put(0xe020, R.mipmap.emoji_2753);
        sSoftbanksMap.put(0xe021, R.mipmap.emoji_2757);
        sSoftbanksMap.put(0xe022, R.mipmap.emoji_2764);
        sSoftbanksMap.put(0xe023, R.mipmap.emoji_1f494);
        sSoftbanksMap.put(0xe024, R.mipmap.emoji_1f550);
        sSoftbanksMap.put(0xe025, R.mipmap.emoji_1f551);
        sSoftbanksMap.put(0xe026, R.mipmap.emoji_1f552);
        sSoftbanksMap.put(0xe027, R.mipmap.emoji_1f553);
        sSoftbanksMap.put(0xe028, R.mipmap.emoji_1f554);
        sSoftbanksMap.put(0xe029, R.mipmap.emoji_1f555);
        sSoftbanksMap.put(0xe02a, R.mipmap.emoji_1f556);
        sSoftbanksMap.put(0xe02b, R.mipmap.emoji_1f557);
        sSoftbanksMap.put(0xe02c, R.mipmap.emoji_1f558);
        sSoftbanksMap.put(0xe02d, R.mipmap.emoji_1f559);
        sSoftbanksMap.put(0xe02e, R.mipmap.emoji_1f55a);
        sSoftbanksMap.put(0xe02f, R.mipmap.emoji_1f55b);
        sSoftbanksMap.put(0xe030, R.mipmap.emoji_1f338);
        sSoftbanksMap.put(0xe031, R.mipmap.emoji_1f531);
        sSoftbanksMap.put(0xe032, R.mipmap.emoji_1f339);
        sSoftbanksMap.put(0xe033, R.mipmap.emoji_1f384);
        sSoftbanksMap.put(0xe034, R.mipmap.emoji_1f48d);
        sSoftbanksMap.put(0xe035, R.mipmap.emoji_1f48e);
        sSoftbanksMap.put(0xe036, R.mipmap.emoji_1f3e0);
        sSoftbanksMap.put(0xe037, R.mipmap.emoji_26ea);
        sSoftbanksMap.put(0xe038, R.mipmap.emoji_1f3e2);
        sSoftbanksMap.put(0xe039, R.mipmap.emoji_1f689);
        sSoftbanksMap.put(0xe03a, R.mipmap.emoji_26fd);
        sSoftbanksMap.put(0xe03b, R.mipmap.emoji_1f5fb);
        sSoftbanksMap.put(0xe03c, R.mipmap.emoji_1f3a4);
        sSoftbanksMap.put(0xe03d, R.mipmap.emoji_1f3a5);
        sSoftbanksMap.put(0xe03e, R.mipmap.emoji_1f3b5);
        sSoftbanksMap.put(0xe03f, R.mipmap.emoji_1f511);
        sSoftbanksMap.put(0xe040, R.mipmap.emoji_1f3b7);
        sSoftbanksMap.put(0xe041, R.mipmap.emoji_1f3b8);
        sSoftbanksMap.put(0xe042, R.mipmap.emoji_1f3ba);
        sSoftbanksMap.put(0xe043, R.mipmap.emoji_1f374);
        sSoftbanksMap.put(0xe044, R.mipmap.emoji_1f377);
        sSoftbanksMap.put(0xe045, R.mipmap.emoji_2615);
        sSoftbanksMap.put(0xe046, R.mipmap.emoji_1f370);
        sSoftbanksMap.put(0xe047, R.mipmap.emoji_1f37a);
        sSoftbanksMap.put(0xe048, R.mipmap.emoji_26c4);
        sSoftbanksMap.put(0xe049, R.mipmap.emoji_2601);
        sSoftbanksMap.put(0xe04a, R.mipmap.emoji_2600);
        sSoftbanksMap.put(0xe04b, R.mipmap.emoji_2614);
        sSoftbanksMap.put(0xe04c, R.mipmap.emoji_1f313);
        sSoftbanksMap.put(0xe04d, R.mipmap.emoji_1f304);
        sSoftbanksMap.put(0xe04e, R.mipmap.emoji_1f47c);
        sSoftbanksMap.put(0xe04f, R.mipmap.emoji_1f431);
        sSoftbanksMap.put(0xe050, R.mipmap.emoji_1f42f);
        sSoftbanksMap.put(0xe051, R.mipmap.emoji_1f43b);
        sSoftbanksMap.put(0xe052, R.mipmap.emoji_1f429);
        sSoftbanksMap.put(0xe053, R.mipmap.emoji_1f42d);
        sSoftbanksMap.put(0xe054, R.mipmap.emoji_1f433);
        sSoftbanksMap.put(0xe055, R.mipmap.emoji_1f427);
        sSoftbanksMap.put(0xe056, R.mipmap.emoji_1f60a);
        sSoftbanksMap.put(0xe057, R.mipmap.emoji_1f603);
        sSoftbanksMap.put(0xe058, R.mipmap.emoji_1f61e);
        sSoftbanksMap.put(0xe059, R.mipmap.emoji_1f620);
        sSoftbanksMap.put(0xe05a, R.mipmap.emoji_1f4a9);
        sSoftbanksMap.put(0xe101, R.mipmap.emoji_1f4ea);
        sSoftbanksMap.put(0xe102, R.mipmap.emoji_1f4ee);
        sSoftbanksMap.put(0xe103, R.mipmap.emoji_1f4e7);
        sSoftbanksMap.put(0xe104, R.mipmap.emoji_1f4f2);
        sSoftbanksMap.put(0xe105, R.mipmap.emoji_1f61c);
        sSoftbanksMap.put(0xe106, R.mipmap.emoji_1f60d);
        sSoftbanksMap.put(0xe107, R.mipmap.emoji_1f631);
        sSoftbanksMap.put(0xe108, R.mipmap.emoji_1f613);
        sSoftbanksMap.put(0xe109, R.mipmap.emoji_1f435);
        sSoftbanksMap.put(0xe10a, R.mipmap.emoji_1f419);
        sSoftbanksMap.put(0xe10b, R.mipmap.emoji_1f437);
        sSoftbanksMap.put(0xe10c, R.mipmap.emoji_1f47d);
        sSoftbanksMap.put(0xe10d, R.mipmap.emoji_1f680);
        sSoftbanksMap.put(0xe10e, R.mipmap.emoji_1f451);
        sSoftbanksMap.put(0xe10f, R.mipmap.emoji_1f4a1);
        sSoftbanksMap.put(0xe110, R.mipmap.emoji_1f331);
        sSoftbanksMap.put(0xe111, R.mipmap.emoji_1f48f);
        sSoftbanksMap.put(0xe112, R.mipmap.emoji_1f381);
        sSoftbanksMap.put(0xe113, R.mipmap.emoji_1f52b);
        sSoftbanksMap.put(0xe114, R.mipmap.emoji_1f50d);
        sSoftbanksMap.put(0xe115, R.mipmap.emoji_1f3c3);
        sSoftbanksMap.put(0xe116, R.mipmap.emoji_1f528);
        sSoftbanksMap.put(0xe117, R.mipmap.emoji_1f386);
        sSoftbanksMap.put(0xe118, R.mipmap.emoji_1f341);
        sSoftbanksMap.put(0xe119, R.mipmap.emoji_1f342);
        sSoftbanksMap.put(0xe11a, R.mipmap.emoji_1f47f);
        sSoftbanksMap.put(0xe11b, R.mipmap.emoji_1f47b);
        sSoftbanksMap.put(0xe11c, R.mipmap.emoji_1f480);
        sSoftbanksMap.put(0xe11d, R.mipmap.emoji_1f525);
        sSoftbanksMap.put(0xe11e, R.mipmap.emoji_1f4bc);
        sSoftbanksMap.put(0xe11f, R.mipmap.emoji_1f4ba);
        sSoftbanksMap.put(0xe120, R.mipmap.emoji_1f354);
        sSoftbanksMap.put(0xe121, R.mipmap.emoji_26f2);
        sSoftbanksMap.put(0xe122, R.mipmap.emoji_26fa);
        sSoftbanksMap.put(0xe123, R.mipmap.emoji_2668);
        sSoftbanksMap.put(0xe124, R.mipmap.emoji_1f3a1);
        sSoftbanksMap.put(0xe125, R.mipmap.emoji_1f3ab);
        sSoftbanksMap.put(0xe126, R.mipmap.emoji_1f4bf);
        sSoftbanksMap.put(0xe127, R.mipmap.emoji_1f4c0);
        sSoftbanksMap.put(0xe128, R.mipmap.emoji_1f4fb);
        sSoftbanksMap.put(0xe129, R.mipmap.emoji_1f4fc);
        sSoftbanksMap.put(0xe12a, R.mipmap.emoji_1f4fa);
        sSoftbanksMap.put(0xe12b, R.mipmap.emoji_1f47e);
        sSoftbanksMap.put(0xe12c, R.mipmap.emoji_303d);
        sSoftbanksMap.put(0xe12d, R.mipmap.emoji_1f004);
        sSoftbanksMap.put(0xe12e, R.mipmap.emoji_1f19a);
        sSoftbanksMap.put(0xe12f, R.mipmap.emoji_1f4b0);
        sSoftbanksMap.put(0xe130, R.mipmap.emoji_1f3af);
        sSoftbanksMap.put(0xe131, R.mipmap.emoji_1f3c6);
        sSoftbanksMap.put(0xe132, R.mipmap.emoji_1f3c1);
        sSoftbanksMap.put(0xe133, R.mipmap.emoji_1f3b0);
        sSoftbanksMap.put(0xe134, R.mipmap.emoji_1f40e);
        sSoftbanksMap.put(0xe135, R.mipmap.emoji_1f6a4);
        sSoftbanksMap.put(0xe136, R.mipmap.emoji_1f6b2);
        sSoftbanksMap.put(0xe137, R.mipmap.emoji_1f6a7);
        sSoftbanksMap.put(0xe138, R.mipmap.emoji_1f6b9);
        sSoftbanksMap.put(0xe139, R.mipmap.emoji_1f6ba);
        sSoftbanksMap.put(0xe13a, R.mipmap.emoji_1f6bc);
        sSoftbanksMap.put(0xe13b, R.mipmap.emoji_1f489);
        sSoftbanksMap.put(0xe13c, R.mipmap.emoji_1f4a4);
        sSoftbanksMap.put(0xe13d, R.mipmap.emoji_26a1);
        sSoftbanksMap.put(0xe13e, R.mipmap.emoji_1f460);
        sSoftbanksMap.put(0xe13f, R.mipmap.emoji_1f6c0);
        sSoftbanksMap.put(0xe140, R.mipmap.emoji_1f6bd);
        sSoftbanksMap.put(0xe141, R.mipmap.emoji_1f50a);
        sSoftbanksMap.put(0xe142, R.mipmap.emoji_1f4e2);
        sSoftbanksMap.put(0xe143, R.mipmap.emoji_1f38c);
        sSoftbanksMap.put(0xe144, R.mipmap.emoji_1f50f);
        sSoftbanksMap.put(0xe145, R.mipmap.emoji_1f513);
        sSoftbanksMap.put(0xe146, R.mipmap.emoji_1f306);
        sSoftbanksMap.put(0xe147, R.mipmap.emoji_1f373);
        sSoftbanksMap.put(0xe148, R.mipmap.emoji_1f4c7);
        sSoftbanksMap.put(0xe149, R.mipmap.emoji_1f4b1);
        sSoftbanksMap.put(0xe14a, R.mipmap.emoji_1f4b9);
        sSoftbanksMap.put(0xe14b, R.mipmap.emoji_1f4e1);
        sSoftbanksMap.put(0xe14c, R.mipmap.emoji_1f4aa);
        sSoftbanksMap.put(0xe14d, R.mipmap.emoji_1f3e6);
        sSoftbanksMap.put(0xe14e, R.mipmap.emoji_1f6a5);
        sSoftbanksMap.put(0xe14f, R.mipmap.emoji_1f17f);
        sSoftbanksMap.put(0xe150, R.mipmap.emoji_1f68f);
        sSoftbanksMap.put(0xe151, R.mipmap.emoji_1f6bb);
        sSoftbanksMap.put(0xe152, R.mipmap.emoji_1f46e);
        sSoftbanksMap.put(0xe153, R.mipmap.emoji_1f3e3);
        sSoftbanksMap.put(0xe154, R.mipmap.emoji_1f3e7);
        sSoftbanksMap.put(0xe155, R.mipmap.emoji_1f3e5);
        sSoftbanksMap.put(0xe156, R.mipmap.emoji_1f3ea);
        sSoftbanksMap.put(0xe157, R.mipmap.emoji_1f3eb);
        sSoftbanksMap.put(0xe158, R.mipmap.emoji_1f3e8);
        sSoftbanksMap.put(0xe159, R.mipmap.emoji_1f68c);
        sSoftbanksMap.put(0xe15a, R.mipmap.emoji_1f695);
        sSoftbanksMap.put(0xe201, R.mipmap.emoji_1f6b6);
        sSoftbanksMap.put(0xe202, R.mipmap.emoji_1f6a2);
        sSoftbanksMap.put(0xe203, R.mipmap.emoji_1f201);
        sSoftbanksMap.put(0xe204, R.mipmap.emoji_1f49f);
        sSoftbanksMap.put(0xe205, R.mipmap.emoji_2734);
        sSoftbanksMap.put(0xe206, R.mipmap.emoji_2733);
        sSoftbanksMap.put(0xe207, R.mipmap.emoji_1f51e);
        sSoftbanksMap.put(0xe208, R.mipmap.emoji_1f6ad);
        sSoftbanksMap.put(0xe209, R.mipmap.emoji_1f530);
        sSoftbanksMap.put(0xe20a, R.mipmap.emoji_267f);
        sSoftbanksMap.put(0xe20b, R.mipmap.emoji_1f4f6);
        sSoftbanksMap.put(0xe20c, R.mipmap.emoji_2665);
        sSoftbanksMap.put(0xe20d, R.mipmap.emoji_2666);
        sSoftbanksMap.put(0xe20e, R.mipmap.emoji_2660);
        sSoftbanksMap.put(0xe20f, R.mipmap.emoji_2663);
        sSoftbanksMap.put(0xe210, R.mipmap.emoji_0023);
        sSoftbanksMap.put(0xe211, R.mipmap.emoji_27bf);
        sSoftbanksMap.put(0xe212, R.mipmap.emoji_1f195);
        sSoftbanksMap.put(0xe213, R.mipmap.emoji_1f199);
        sSoftbanksMap.put(0xe214, R.mipmap.emoji_1f192);
        sSoftbanksMap.put(0xe215, R.mipmap.emoji_1f236);
        sSoftbanksMap.put(0xe216, R.mipmap.emoji_1f21a);
        sSoftbanksMap.put(0xe217, R.mipmap.emoji_1f237);
        sSoftbanksMap.put(0xe218, R.mipmap.emoji_1f238);
        sSoftbanksMap.put(0xe219, R.mipmap.emoji_1f534);
        sSoftbanksMap.put(0xe21a, R.mipmap.emoji_1f532);
        sSoftbanksMap.put(0xe21b, R.mipmap.emoji_1f533);
        sSoftbanksMap.put(0xe21c, R.mipmap.emoji_0031);
        sSoftbanksMap.put(0xe21d, R.mipmap.emoji_0032);
        sSoftbanksMap.put(0xe21e, R.mipmap.emoji_0033);
        sSoftbanksMap.put(0xe21f, R.mipmap.emoji_0034);
        sSoftbanksMap.put(0xe220, R.mipmap.emoji_0035);
        sSoftbanksMap.put(0xe221, R.mipmap.emoji_0036);
        sSoftbanksMap.put(0xe222, R.mipmap.emoji_0037);
        sSoftbanksMap.put(0xe223, R.mipmap.emoji_0038);
        sSoftbanksMap.put(0xe224, R.mipmap.emoji_0039);
        sSoftbanksMap.put(0xe225, R.mipmap.emoji_0030);
        sSoftbanksMap.put(0xe226, R.mipmap.emoji_1f250);
        sSoftbanksMap.put(0xe227, R.mipmap.emoji_1f239);
        sSoftbanksMap.put(0xe228, R.mipmap.emoji_1f202);
        sSoftbanksMap.put(0xe229, R.mipmap.emoji_1f194);
        sSoftbanksMap.put(0xe22a, R.mipmap.emoji_1f235);
        sSoftbanksMap.put(0xe22b, R.mipmap.emoji_1f233);
        sSoftbanksMap.put(0xe22c, R.mipmap.emoji_1f22f);
        sSoftbanksMap.put(0xe22d, R.mipmap.emoji_1f23a);
        sSoftbanksMap.put(0xe22e, R.mipmap.emoji_1f446);
        sSoftbanksMap.put(0xe22f, R.mipmap.emoji_1f447);
        sSoftbanksMap.put(0xe230, R.mipmap.emoji_1f448);
        sSoftbanksMap.put(0xe231, R.mipmap.emoji_1f449);
        sSoftbanksMap.put(0xe232, R.mipmap.emoji_2b06);
        sSoftbanksMap.put(0xe233, R.mipmap.emoji_2b07);
        sSoftbanksMap.put(0xe234, R.mipmap.emoji_27a1);
        sSoftbanksMap.put(0xe235, R.mipmap.emoji_1f519);
        sSoftbanksMap.put(0xe236, R.mipmap.emoji_2197);
        sSoftbanksMap.put(0xe237, R.mipmap.emoji_2196);
        sSoftbanksMap.put(0xe238, R.mipmap.emoji_2198);
        sSoftbanksMap.put(0xe239, R.mipmap.emoji_2199);
        sSoftbanksMap.put(0xe23a, R.mipmap.emoji_25b6);
        sSoftbanksMap.put(0xe23b, R.mipmap.emoji_25c0);
        sSoftbanksMap.put(0xe23c, R.mipmap.emoji_23e9);
        sSoftbanksMap.put(0xe23d, R.mipmap.emoji_23ea);
        sSoftbanksMap.put(0xe23e, R.mipmap.emoji_1f52e);
        sSoftbanksMap.put(0xe23f, R.mipmap.emoji_2648);
        sSoftbanksMap.put(0xe240, R.mipmap.emoji_2649);
        sSoftbanksMap.put(0xe241, R.mipmap.emoji_264a);
        sSoftbanksMap.put(0xe242, R.mipmap.emoji_264b);
        sSoftbanksMap.put(0xe243, R.mipmap.emoji_264c);
        sSoftbanksMap.put(0xe244, R.mipmap.emoji_264d);
        sSoftbanksMap.put(0xe245, R.mipmap.emoji_264e);
        sSoftbanksMap.put(0xe246, R.mipmap.emoji_264f);
        sSoftbanksMap.put(0xe247, R.mipmap.emoji_2650);
        sSoftbanksMap.put(0xe248, R.mipmap.emoji_2651);
        sSoftbanksMap.put(0xe249, R.mipmap.emoji_2652);
        sSoftbanksMap.put(0xe24a, R.mipmap.emoji_2653);
        sSoftbanksMap.put(0xe24b, R.mipmap.emoji_26ce);
        sSoftbanksMap.put(0xe24c, R.mipmap.emoji_1f51d);
        sSoftbanksMap.put(0xe24d, R.mipmap.emoji_1f197);
        sSoftbanksMap.put(0xe24e, R.mipmap.emoji_00a9);
        sSoftbanksMap.put(0xe24f, R.mipmap.emoji_00ae);
        sSoftbanksMap.put(0xe250, R.mipmap.emoji_1f4f3);
        sSoftbanksMap.put(0xe251, R.mipmap.emoji_1f4f4);
        sSoftbanksMap.put(0xe252, R.mipmap.emoji_26a0);
        sSoftbanksMap.put(0xe253, R.mipmap.emoji_1f481);
        sSoftbanksMap.put(0xe301, R.mipmap.emoji_1f4c3);
        sSoftbanksMap.put(0xe302, R.mipmap.emoji_1f454);
        sSoftbanksMap.put(0xe303, R.mipmap.emoji_1f33a);
        sSoftbanksMap.put(0xe304, R.mipmap.emoji_1f337);
        sSoftbanksMap.put(0xe305, R.mipmap.emoji_1f33b);
        sSoftbanksMap.put(0xe306, R.mipmap.emoji_1f490);
        sSoftbanksMap.put(0xe307, R.mipmap.emoji_1f334);
        sSoftbanksMap.put(0xe308, R.mipmap.emoji_1f335);
        sSoftbanksMap.put(0xe309, R.mipmap.emoji_1f6be);
        sSoftbanksMap.put(0xe30a, R.mipmap.emoji_1f3a7);
        sSoftbanksMap.put(0xe30b, R.mipmap.emoji_1f376);
        sSoftbanksMap.put(0xe30c, R.mipmap.emoji_1f37b);
        sSoftbanksMap.put(0xe30d, R.mipmap.emoji_3297);
        sSoftbanksMap.put(0xe30e, R.mipmap.emoji_1f6ac);
        sSoftbanksMap.put(0xe30f, R.mipmap.emoji_1f48a);
        sSoftbanksMap.put(0xe310, R.mipmap.emoji_1f388);
        sSoftbanksMap.put(0xe311, R.mipmap.emoji_1f4a3);
        sSoftbanksMap.put(0xe312, R.mipmap.emoji_1f389);
        sSoftbanksMap.put(0xe313, R.mipmap.emoji_2702);
        sSoftbanksMap.put(0xe314, R.mipmap.emoji_1f380);
        sSoftbanksMap.put(0xe315, R.mipmap.emoji_3299);
        sSoftbanksMap.put(0xe316, R.mipmap.emoji_1f4bd);
        sSoftbanksMap.put(0xe317, R.mipmap.emoji_1f4e3);
        sSoftbanksMap.put(0xe318, R.mipmap.emoji_1f452);
        sSoftbanksMap.put(0xe319, R.mipmap.emoji_1f457);
        sSoftbanksMap.put(0xe31a, R.mipmap.emoji_1f461);
        sSoftbanksMap.put(0xe31b, R.mipmap.emoji_1f462);
        sSoftbanksMap.put(0xe31c, R.mipmap.emoji_1f484);
        sSoftbanksMap.put(0xe31d, R.mipmap.emoji_1f485);
        sSoftbanksMap.put(0xe31e, R.mipmap.emoji_1f486);
        sSoftbanksMap.put(0xe31f, R.mipmap.emoji_1f487);
        sSoftbanksMap.put(0xe320, R.mipmap.emoji_1f488);
        sSoftbanksMap.put(0xe321, R.mipmap.emoji_1f458);
        sSoftbanksMap.put(0xe322, R.mipmap.emoji_1f459);
        sSoftbanksMap.put(0xe323, R.mipmap.emoji_1f45c);
        sSoftbanksMap.put(0xe324, R.mipmap.emoji_1f3ac);
        sSoftbanksMap.put(0xe325, R.mipmap.emoji_1f514);
        sSoftbanksMap.put(0xe326, R.mipmap.emoji_1f3b6);
        sSoftbanksMap.put(0xe327, R.mipmap.emoji_1f493);
        sSoftbanksMap.put(0xe328, R.mipmap.emoji_1f48c);
        sSoftbanksMap.put(0xe329, R.mipmap.emoji_1f498);
        sSoftbanksMap.put(0xe32a, R.mipmap.emoji_1f499);
        sSoftbanksMap.put(0xe32b, R.mipmap.emoji_1f49a);
        sSoftbanksMap.put(0xe32c, R.mipmap.emoji_1f49b);
        sSoftbanksMap.put(0xe32d, R.mipmap.emoji_1f49c);
        sSoftbanksMap.put(0xe32e, R.mipmap.emoji_2728);
        sSoftbanksMap.put(0xe32f, R.mipmap.emoji_2b50);
        sSoftbanksMap.put(0xe330, R.mipmap.emoji_1f4a8);
        sSoftbanksMap.put(0xe331, R.mipmap.emoji_1f4a6);
        sSoftbanksMap.put(0xe332, R.mipmap.emoji_2b55);
        sSoftbanksMap.put(0xe333, R.mipmap.emoji_2716);
        sSoftbanksMap.put(0xe334, R.mipmap.emoji_1f4a2);
        sSoftbanksMap.put(0xe335, R.mipmap.emoji_1f31f);
        sSoftbanksMap.put(0xe336, R.mipmap.emoji_2754);
        sSoftbanksMap.put(0xe337, R.mipmap.emoji_2755);
        sSoftbanksMap.put(0xe338, R.mipmap.emoji_1f375);
        sSoftbanksMap.put(0xe339, R.mipmap.emoji_1f35e);
        sSoftbanksMap.put(0xe33a, R.mipmap.emoji_1f366);
        sSoftbanksMap.put(0xe33b, R.mipmap.emoji_1f35f);
        sSoftbanksMap.put(0xe33c, R.mipmap.emoji_1f361);
        sSoftbanksMap.put(0xe33d, R.mipmap.emoji_1f358);
        sSoftbanksMap.put(0xe33e, R.mipmap.emoji_1f35a);
        sSoftbanksMap.put(0xe33f, R.mipmap.emoji_1f35d);
        sSoftbanksMap.put(0xe340, R.mipmap.emoji_1f35c);
        sSoftbanksMap.put(0xe341, R.mipmap.emoji_1f35b);
        sSoftbanksMap.put(0xe342, R.mipmap.emoji_1f359);
        sSoftbanksMap.put(0xe343, R.mipmap.emoji_1f362);
        sSoftbanksMap.put(0xe344, R.mipmap.emoji_1f363);
        sSoftbanksMap.put(0xe345, R.mipmap.emoji_1f34e);
        sSoftbanksMap.put(0xe346, R.mipmap.emoji_1f34a);
        sSoftbanksMap.put(0xe347, R.mipmap.emoji_1f353);
        sSoftbanksMap.put(0xe348, R.mipmap.emoji_1f349);
        sSoftbanksMap.put(0xe349, R.mipmap.emoji_1f345);
        sSoftbanksMap.put(0xe34a, R.mipmap.emoji_1f346);
        sSoftbanksMap.put(0xe34b, R.mipmap.emoji_1f382);
        sSoftbanksMap.put(0xe34c, R.mipmap.emoji_1f371);
        sSoftbanksMap.put(0xe34d, R.mipmap.emoji_1f372);
        sSoftbanksMap.put(0xe401, R.mipmap.emoji_1f625);
        sSoftbanksMap.put(0xe402, R.mipmap.emoji_1f60f);
        sSoftbanksMap.put(0xe403, R.mipmap.emoji_1f614);
        sSoftbanksMap.put(0xe404, R.mipmap.emoji_1f601);
        sSoftbanksMap.put(0xe405, R.mipmap.emoji_1f609);
        sSoftbanksMap.put(0xe406, R.mipmap.emoji_1f623);
        sSoftbanksMap.put(0xe407, R.mipmap.emoji_1f616);
        sSoftbanksMap.put(0xe408, R.mipmap.emoji_1f62a);
        sSoftbanksMap.put(0xe409, R.mipmap.emoji_1f445);
        sSoftbanksMap.put(0xe40a, R.mipmap.emoji_1f606);
        sSoftbanksMap.put(0xe40b, R.mipmap.emoji_1f628);
        sSoftbanksMap.put(0xe40c, R.mipmap.emoji_1f637);
        sSoftbanksMap.put(0xe40d, R.mipmap.emoji_1f633);
        sSoftbanksMap.put(0xe40e, R.mipmap.emoji_1f612);
        sSoftbanksMap.put(0xe40f, R.mipmap.emoji_1f630);
        sSoftbanksMap.put(0xe410, R.mipmap.emoji_1f632);
        sSoftbanksMap.put(0xe411, R.mipmap.emoji_1f62d);
        sSoftbanksMap.put(0xe412, R.mipmap.emoji_1f602);
        sSoftbanksMap.put(0xe413, R.mipmap.emoji_1f622);
        sSoftbanksMap.put(0xe414, R.mipmap.emoji_263a);
        sSoftbanksMap.put(0xe415, R.mipmap.emoji_1f605);
        sSoftbanksMap.put(0xe416, R.mipmap.emoji_1f621);
        sSoftbanksMap.put(0xe417, R.mipmap.emoji_1f61a);
        sSoftbanksMap.put(0xe418, R.mipmap.emoji_1f618);
        sSoftbanksMap.put(0xe419, R.mipmap.emoji_1f440);
        sSoftbanksMap.put(0xe41a, R.mipmap.emoji_1f443);
        sSoftbanksMap.put(0xe41b, R.mipmap.emoji_1f442);
        sSoftbanksMap.put(0xe41c, R.mipmap.emoji_1f444);
        sSoftbanksMap.put(0xe41d, R.mipmap.emoji_1f64f);
        sSoftbanksMap.put(0xe41e, R.mipmap.emoji_1f44b);
        sSoftbanksMap.put(0xe41f, R.mipmap.emoji_1f44f);
        sSoftbanksMap.put(0xe420, R.mipmap.emoji_1f44c);
        sSoftbanksMap.put(0xe421, R.mipmap.emoji_1f44e);
        sSoftbanksMap.put(0xe422, R.mipmap.emoji_1f450);
        sSoftbanksMap.put(0xe423, R.mipmap.emoji_1f645);
        sSoftbanksMap.put(0xe424, R.mipmap.emoji_1f646);
        sSoftbanksMap.put(0xe425, R.mipmap.emoji_1f491);
        sSoftbanksMap.put(0xe426, R.mipmap.emoji_1f647);
        sSoftbanksMap.put(0xe427, R.mipmap.emoji_1f64c);
        sSoftbanksMap.put(0xe428, R.mipmap.emoji_1f46b);
        sSoftbanksMap.put(0xe429, R.mipmap.emoji_1f46f);
        sSoftbanksMap.put(0xe42a, R.mipmap.emoji_1f3c0);
        sSoftbanksMap.put(0xe42b, R.mipmap.emoji_1f3c8);
        sSoftbanksMap.put(0xe42c, R.mipmap.emoji_1f3b1);
        sSoftbanksMap.put(0xe42d, R.mipmap.emoji_1f3ca);
        sSoftbanksMap.put(0xe42e, R.mipmap.emoji_1f699);
        sSoftbanksMap.put(0xe42f, R.mipmap.emoji_1f69a);
        sSoftbanksMap.put(0xe430, R.mipmap.emoji_1f692);
        sSoftbanksMap.put(0xe431, R.mipmap.emoji_1f691);
        sSoftbanksMap.put(0xe432, R.mipmap.emoji_1f693);
        sSoftbanksMap.put(0xe433, R.mipmap.emoji_1f3a2);
        sSoftbanksMap.put(0xe434, R.mipmap.emoji_1f687);
        sSoftbanksMap.put(0xe435, R.mipmap.emoji_1f684);
        sSoftbanksMap.put(0xe436, R.mipmap.emoji_1f38d);
        sSoftbanksMap.put(0xe437, R.mipmap.emoji_1f49d);
        sSoftbanksMap.put(0xe438, R.mipmap.emoji_1f38e);
        sSoftbanksMap.put(0xe439, R.mipmap.emoji_1f393);
        sSoftbanksMap.put(0xe43a, R.mipmap.emoji_1f392);
        sSoftbanksMap.put(0xe43b, R.mipmap.emoji_1f38f);
        sSoftbanksMap.put(0xe43c, R.mipmap.emoji_1f302);
        sSoftbanksMap.put(0xe43d, R.mipmap.emoji_1f492);
        sSoftbanksMap.put(0xe43e, R.mipmap.emoji_1f30a);
        sSoftbanksMap.put(0xe43f, R.mipmap.emoji_1f367);
        sSoftbanksMap.put(0xe440, R.mipmap.emoji_1f387);
        sSoftbanksMap.put(0xe441, R.mipmap.emoji_1f41a);
        sSoftbanksMap.put(0xe442, R.mipmap.emoji_1f390);
        sSoftbanksMap.put(0xe443, R.mipmap.emoji_1f300);
        sSoftbanksMap.put(0xe444, R.mipmap.emoji_1f33e);
        sSoftbanksMap.put(0xe445, R.mipmap.emoji_1f383);
        sSoftbanksMap.put(0xe446, R.mipmap.emoji_1f391);
        sSoftbanksMap.put(0xe447, R.mipmap.emoji_1f343);
        sSoftbanksMap.put(0xe448, R.mipmap.emoji_1f385);
        sSoftbanksMap.put(0xe449, R.mipmap.emoji_1f305);
        sSoftbanksMap.put(0xe44a, R.mipmap.emoji_1f307);
        sSoftbanksMap.put(0xe44b, R.mipmap.emoji_1f303);
        sSoftbanksMap.put(0xe44b, R.mipmap.emoji_1f30c);
        sSoftbanksMap.put(0xe44c, R.mipmap.emoji_1f308);
        sSoftbanksMap.put(0xe501, R.mipmap.emoji_1f3e9);
        sSoftbanksMap.put(0xe502, R.mipmap.emoji_1f3a8);
        sSoftbanksMap.put(0xe503, R.mipmap.emoji_1f3a9);
        sSoftbanksMap.put(0xe504, R.mipmap.emoji_1f3ec);
        sSoftbanksMap.put(0xe505, R.mipmap.emoji_1f3ef);
        sSoftbanksMap.put(0xe506, R.mipmap.emoji_1f3f0);
        sSoftbanksMap.put(0xe507, R.mipmap.emoji_1f3a6);
        sSoftbanksMap.put(0xe508, R.mipmap.emoji_1f3ed);
        sSoftbanksMap.put(0xe509, R.mipmap.emoji_1f5fc);
        sSoftbanksMap.put(0xe50b, R.mipmap.emoji_1f1ef_1f1f5);
        sSoftbanksMap.put(0xe50c, R.mipmap.emoji_1f1fa_1f1f8);
        sSoftbanksMap.put(0xe50d, R.mipmap.emoji_1f1eb_1f1f7);
        sSoftbanksMap.put(0xe50e, R.mipmap.emoji_1f1e9_1f1ea);
        sSoftbanksMap.put(0xe50f, R.mipmap.emoji_1f1ee_1f1f9);
        sSoftbanksMap.put(0xe510, R.mipmap.emoji_1f1ec_1f1e7);
        sSoftbanksMap.put(0xe511, R.mipmap.emoji_1f1ea_1f1f8);
        sSoftbanksMap.put(0xe512, R.mipmap.emoji_1f1f7_1f1fa);
        sSoftbanksMap.put(0xe513, R.mipmap.emoji_1f1e8_1f1f3);
        sSoftbanksMap.put(0xe514, R.mipmap.emoji_1f1f0_1f1f7);
        sSoftbanksMap.put(0xe515, R.mipmap.emoji_1f471);
        sSoftbanksMap.put(0xe516, R.mipmap.emoji_1f472);
        sSoftbanksMap.put(0xe517, R.mipmap.emoji_1f473);
        sSoftbanksMap.put(0xe518, R.mipmap.emoji_1f474);
        sSoftbanksMap.put(0xe519, R.mipmap.emoji_1f475);
        sSoftbanksMap.put(0xe51a, R.mipmap.emoji_1f476);
        sSoftbanksMap.put(0xe51b, R.mipmap.emoji_1f477);
        sSoftbanksMap.put(0xe51c, R.mipmap.emoji_1f478);
        sSoftbanksMap.put(0xe51d, R.mipmap.emoji_1f5fd);
        sSoftbanksMap.put(0xe51e, R.mipmap.emoji_1f482);
        sSoftbanksMap.put(0xe51f, R.mipmap.emoji_1f483);
        sSoftbanksMap.put(0xe520, R.mipmap.emoji_1f42c);
        sSoftbanksMap.put(0xe521, R.mipmap.emoji_1f426);
        sSoftbanksMap.put(0xe522, R.mipmap.emoji_1f420);
        sSoftbanksMap.put(0xe523, R.mipmap.emoji_1f423);
        sSoftbanksMap.put(0xe524, R.mipmap.emoji_1f439);
        sSoftbanksMap.put(0xe525, R.mipmap.emoji_1f41b);
        sSoftbanksMap.put(0xe526, R.mipmap.emoji_1f418);
        sSoftbanksMap.put(0xe527, R.mipmap.emoji_1f428);
        sSoftbanksMap.put(0xe528, R.mipmap.emoji_1f412);
        sSoftbanksMap.put(0xe529, R.mipmap.emoji_1f411);
        sSoftbanksMap.put(0xe52a, R.mipmap.emoji_1f43a);
        sSoftbanksMap.put(0xe52b, R.mipmap.emoji_1f42e);
        sSoftbanksMap.put(0xe52c, R.mipmap.emoji_1f430);
        sSoftbanksMap.put(0xe52d, R.mipmap.emoji_1f40d);
        sSoftbanksMap.put(0xe52e, R.mipmap.emoji_1f414);
        sSoftbanksMap.put(0xe52f, R.mipmap.emoji_1f417);
        sSoftbanksMap.put(0xe530, R.mipmap.emoji_1f42b);
        sSoftbanksMap.put(0xe531, R.mipmap.emoji_1f438);
        sSoftbanksMap.put(0xe532, R.mipmap.emoji_1f170);
        sSoftbanksMap.put(0xe533, R.mipmap.emoji_1f171);
        sSoftbanksMap.put(0xe534, R.mipmap.emoji_1f18e);
        sSoftbanksMap.put(0xe535, R.mipmap.emoji_1f17e);
        sSoftbanksMap.put(0xe536, R.mipmap.emoji_1f43e);
        sSoftbanksMap.put(0xe537, R.mipmap.emoji_2122);
    }

    private static boolean isSoftBankEmoji(char c) {
        return ((c >> 12) == 0xe);
    }

    public static int getEmojiResource(Context context, int codePoint) {
        return sEmojisMap.get(codePoint);
    }

    private static int getSoftbankEmojiResource(char c) {
        return sSoftbanksMap.get(c);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int textSize) {
        addEmojis(context, text, emojiSize, textSize, 0, -1, false);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param index
     * @param length
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int textSize, int index, int length) {
        addEmojis(context, text, emojiSize, textSize, index, length, false);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param useSystemDefault
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int textSize, boolean useSystemDefault) {
        addEmojis(context, text, emojiSize, textSize, 0, -1, useSystemDefault);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param index
     * @param length
     * @param useSystemDefault
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int textSize, int index, int length, boolean useSystemDefault) {
        if (useSystemDefault) {
            return;
        }

        int textLength = text.length();
        int textLengthToProcessMax = textLength - index;
        int textLengthToProcess = length < 0 || length >= textLengthToProcessMax ? textLength : (length+index);

        // remove spans throughout all text
        EmojiconSpan[] oldSpans = text.getSpans(0, textLength, EmojiconSpan.class);
        for (int i = 0; i < oldSpans.length; i++) {
            text.removeSpan(oldSpans[i]);
        }

        int skip;
        for (int i = index; i < textLengthToProcess; i += skip) {
            skip = 0;
            int icon = 0;
            char c = text.charAt(i);
            if (isSoftBankEmoji(c)) {
                icon = getSoftbankEmojiResource(c);
                skip = icon == 0 ? 0 : 1;
            }

            if (icon == 0) {
                int unicode = Character.codePointAt(text, i);
                skip = Character.charCount(unicode);

                if (unicode > 0xff) {
                    icon = getEmojiResource(context, unicode);
                }

                if (icon == 0 && i + skip < textLengthToProcess) {
                    int followUnicode = Character.codePointAt(text, i + skip);
                    if (followUnicode == 0x20e3) {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            case 0x0031:
                                icon = R.mipmap.emoji_0031;
                                break;
                            case 0x0032:
                                icon = R.mipmap.emoji_0032;
                                break;
                            case 0x0033:
                                icon = R.mipmap.emoji_0033;
                                break;
                            case 0x0034:
                                icon = R.mipmap.emoji_0034;
                                break;
                            case 0x0035:
                                icon = R.mipmap.emoji_0035;
                                break;
                            case 0x0036:
                                icon = R.mipmap.emoji_0036;
                                break;
                            case 0x0037:
                                icon = R.mipmap.emoji_0037;
                                break;
                            case 0x0038:
                                icon = R.mipmap.emoji_0038;
                                break;
                            case 0x0039:
                                icon = R.mipmap.emoji_0039;
                                break;
                            case 0x0030:
                                icon = R.mipmap.emoji_0030;
                                break;
                            case 0x0023:
                                icon = R.mipmap.emoji_0023;
                                break;
                            default:
                                followSkip = 0;
                                break;
                        }
                        skip += followSkip;
                    } else {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            case 0x1f1ef:
                                icon = (followUnicode == 0x1f1f5) ? R.mipmap.emoji_1f1ef_1f1f5 : 0;
                                break;
                            case 0x1f1fa:
                                icon = (followUnicode == 0x1f1f8) ? R.mipmap.emoji_1f1fa_1f1f8 : 0;
                                break;
                            case 0x1f1eb:
                                icon = (followUnicode == 0x1f1f7) ? R.mipmap.emoji_1f1eb_1f1f7 : 0;
                                break;
                            case 0x1f1e9:
                                icon = (followUnicode == 0x1f1ea) ? R.mipmap.emoji_1f1e9_1f1ea : 0;
                                break;
                            case 0x1f1ee:
                                icon = (followUnicode == 0x1f1f9) ? R.mipmap.emoji_1f1ee_1f1f9 : 0;
                                break;
                            case 0x1f1ec:
                                icon = (followUnicode == 0x1f1e7) ? R.mipmap.emoji_1f1ec_1f1e7 : 0;
                                break;
                            case 0x1f1ea:
                                icon = (followUnicode == 0x1f1f8) ? R.mipmap.emoji_1f1ea_1f1f8 : 0;
                                break;
                            case 0x1f1f7:
                                icon = (followUnicode == 0x1f1fa) ? R.mipmap.emoji_1f1f7_1f1fa : 0;
                                break;
                            case 0x1f1e8:
                                icon = (followUnicode == 0x1f1f3) ? R.mipmap.emoji_1f1e8_1f1f3 : 0;
                                break;
                            case 0x1f1f0:
                                icon = (followUnicode == 0x1f1f7) ? R.mipmap.emoji_1f1f0_1f1f7 : 0;
                                break;
                            default:
                                followSkip = 0;
                                break;
                        }
                        skip += followSkip;
                    }
                }
            }

            if (icon > 0) {
                text.setSpan(new EmojiconSpan(context, icon, emojiSize, textSize), i, i + skip, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
