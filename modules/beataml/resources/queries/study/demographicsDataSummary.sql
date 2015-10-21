/*
 * Copyright (c) 2015 LabKey Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
SELECT
total.patientId,

CASE
  WHEN max(total.hasRnaSeq) = 1 THEN 'Y'
  ELSE null
END as hasRnaSeq,
CASE
  WHEN max(total.hasWGS) = 1 THEN 'Y'
  ELSE NULL
END as hasWgs,
CASE
  WHEN max(total.hasDrugResponse) = 1 THEN 'Y'
  ELSE NULL
END as hasDrugResponse

FROM (
  SELECT a.patientId, 1 as hasRnaSeq, 0 as hasWGS, 0 as hasDrugResponse FROM study.rnaseq a
  UNION
  SELECT b.patientId, 0 as hasRnaSeq, 1 as hasWGS, 0 as hasDrugResponse FROM study.wgs b
  UNION
  SELECT c.patientId, 0 as hasRnaSeq, 0 as hasWGS, 0 as hasDrugResponse FROM study.drugResponse c
) as total GROUP BY patientId
