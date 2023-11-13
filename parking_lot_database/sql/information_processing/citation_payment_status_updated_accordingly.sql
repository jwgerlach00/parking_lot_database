UPDATE Citations
SET paymentStatus=true
WHERE citationNum=1 AND appealRequested=true;
-- Unsure whether appealRequested should be here.
-- Narrative says "Citation appeals can be requested and citation payment status updated accordingly".