/**
 * @file    PoseidonData.hpp
 * @author  Pratchaya Khansomboon (pratchaya.k.git@gmail.com)
 * @brief   Data interface for later implementation
 * @version 0.1.0
 * @date 2021-04-12
 *
 * @copyright Copyright (c) 2021
 *
 */
#pragma once

namespace poseidon {

    class JSONData {
       public:
        virtual ~JSONData() = default;
        virtual char* toJSON() = 0;
    };

    class CSVData {
       public:
        virtual ~CSVData() = default;
        virtual char* toCSV() = 0;
    };

}  // namespace poseidon
