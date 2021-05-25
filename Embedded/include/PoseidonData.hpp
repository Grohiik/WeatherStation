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
        virtual char* toCSV(const bool& headerOn) = 0;
    };

}  // namespace poseidon
