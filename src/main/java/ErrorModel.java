/*
 * Copyright (c) 2016. Zuercher Hochschule fuer Angewandte Wissenschaften
 *  All Rights Reserved.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License"); you may
 *     not use this file except in compliance with the License. You may obtain
 *     a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *     WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *     License for the specific language governing permissions and limitations
 *     under the License.
 */

/*
 *     Author: Piyush Harsh,
 *     URL: piyush-harsh.info
 */

public class ErrorModel
{
    private static boolean isCustomModelSet;

    public static void activateCustomModel()
    {
        isCustomModelSet = true;
    }

    public static boolean getCustomErrorModelState()
    {
        return isCustomModelSet;
    }

    public static void deactivateCustomModel()
    {
        isCustomModelSet = false;
    }

    public static float getErrorFactor(float displacement)
    {
        //add your algorithm here - return value should be between 0.0 and 100.00
        //therefore please scale the return probability factor accordingly
        //input is the displace of the data collector node from sensor node

        return 0.0f;
    }
}
